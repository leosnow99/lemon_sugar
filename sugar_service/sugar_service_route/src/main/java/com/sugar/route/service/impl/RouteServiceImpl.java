package com.sugar.route.service.impl;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.route.algorithm.AllocationAlgorithm;
import com.sugar.route.netty.ChatServerHandler;
import com.sugar.route.netty.ChatServerHolder;
import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.ForwardMessage;
import com.sugar.route.pojo.UserChatInfo;
import com.sugar.route.service.RouteService;
import com.sugar.route.util.ChatServiceHandle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;

/**
 * @author LEOSNOW
 */
@Service
@Slf4j
public class RouteServiceImpl implements RouteService {
    @Autowired
    private AllocationAlgorithm allocationAlgorithm;

    @Autowired
    private ChatServiceHandle chatServiceHandle;

    @Override
    public void register(ChatServerInfo chatServerInfo) {
        chatServiceHandle.register(chatServerInfo);
//        Client.connectChatServer(chatServerInfo);
    }

    @Override
    public ChatServerInfo getChatServer(UserChatInfo userChatInfo) {
        log.info(userChatInfo.getIp() + " 获取ip地址!");
        return allocationAlgorithm.getServer();
    }

    @Override
    public void forwardMessage(ForwardMessage message) {
        if (StringUtils.isEmpty(message.getToUserId())) {
            return;
        }
        chatServiceHandle.forwardMessage(message);
    }

    @Override
    public ChatServerInfo getChatServer() {
        return allocationAlgorithm.getServer();
    }

    @Override
    public void shutDownServer(ChatServerInfo chatServerInfo) {
//        if (chatServerInfo != null) {
//            final ChatMessage.Message message = ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.SHUTDOWN_USER, chatServerInfo.getId());
//            ChatServerHandler.writeMsg(chatServerInfo, message);
//        }
        log.warn("now this is not support");
    }

    @Override
    public Long getCountUserServer(ChatServerInfo chatServerInfo) {
        return null;
    }

    @Override
    public void shutdownUser(String userId) {
        log.warn("No implements!");
    }

    @Override
    public void pushAllUserMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            ChatServerHandler.writeMsgToAll(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER
                    , message));
        }
    }

    @PreDestroy
    public void destroy() {
        ChatServerHolder.destroy();
    }
}
