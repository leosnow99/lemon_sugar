package com.sugar.route.util;

import com.sugar.route.dao.ChatServerDao;
import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.ForwardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bytedance
 */

@Slf4j
@Component
public class ChatServiceHandle {
    @Autowired
    private ForwardUtil forwardUtil;

    @Autowired
    private ChatServerDao chatServerDao;

    /**
     * key: chatServerId value: ChatServerInfo
     * 管理注册到本route 的 chatServer
     */
    private static final Map<String, ChatServerInfo> REGISTER_CHAT_SERVER = new HashMap<>();


    public void register(ChatServerInfo info) {
        if (info != null && !StringUtils.isEmpty(info.getId())) {
            log.info("chatServer [id: " + info.getId() + " ip: " + info.getAddress() + ":" + info.getPort() + "] register");
            REGISTER_CHAT_SERVER.put(info.getId(), info);

            // IM 实例存储到 redis 中进行保存
            chatServerDao.setCountByChatServerId(info);
        }
    }

    public static List<ChatServerInfo> getAllChatServer() {
        return new ArrayList<>(REGISTER_CHAT_SERVER.values());

    }

    public void check(ChatServerInfo chatServerInfo) {
        log.info("begin heath check serverId: " + chatServerInfo.getId());

        String instance = "http://" + chatServerInfo.getAddress() + ":" + chatServerInfo.getHttpPort();
        log.info("instance: " + instance);
        WebClient webClient = WebClient.builder().baseUrl(instance).build();
        // 异步处理健康检查并将结果更新到 redis
        webClient.get()
                .uri("/route/health")
                .retrieve()
                .bodyToMono(Integer.class)
                .doOnError(e -> chatServerDao.deleteChatServer(chatServerInfo.getId()))
                .subscribe(e -> {
                    log.info("complete heath check serverId: " + chatServerInfo.getId() + "new count: " + e);
                    chatServerInfo.setCount(e);
                    chatServerDao.setCountByChatServerId(chatServerInfo);
                });
    }

    public void forwardMessage(ForwardMessage message) {
        String toUserId = message.getToUserId();
        String serverId = forwardUtil.getServerIdByUserId(toUserId);
        if (StringUtils.isEmpty(serverId)) {
            return;
        }

        // 对发送消息的实例进行判断 如果已经失效则将服务注册到本route
        ChatServerInfo chatServer = chatServerDao.getChatServerByChatServerId(serverId);
        if (chatServer == null) {
            log.warn("chatServer maybe not register any route chatServerId: " + serverId);

            ServiceInstance serviceInstance = ConsulUtil.getChatServerInstanceById(serverId);
            if (serviceInstance == null) {
                log.warn("get chatServer failed chatServerId: " + serverId);
                return;
            }

            String portStr = serviceInstance.getMetadata().get("rote-portStr");
            if (StringUtils.isEmpty(portStr)) {
                portStr = "7100";
            }
            Integer port = Integer.valueOf(portStr);

            chatServer = new ChatServerInfo(serverId, serviceInstance.getHost(), port, serviceInstance.getPort(), 0);
            register(chatServer);
        }

        forwardUtil.sendMessage(chatServer, message);
    }
}
