package com.sugar.chat.service.impl;

import com.sugar.chat.dao.ChatMsgRepository;
import com.sugar.chat.model.ChatMsg;
import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.service.ChatService;
import com.sugar.chat.util.ChannelSocketHolder;
import com.sugar.chat.util.IdWorker;
import com.sugar.route.feign.RouteFeign;
import com.sugar.route.pojo.ForwardMessage;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author LEOSNOW
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RouteFeign routeFeign;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ChatMsgRepository chatMsgRepository;

    @Value("${chat-server.redis.user_server_prefix}")
    private String userServerPrefix;

    @Value("${netty-service.id}")
    private String chatServerId;

    private final String messagePrefix = "message_";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void shutdownAllUser() {
        ChannelSocketHolder.closeAllUser();
    }

    @Override
    public void shutdownUser(String userId) {
        ChannelSocketHolder.closeUser(userId);
    }

    @Override
    public void registerRoute(String routeId, NioSocketChannel channel) {
        ChannelSocketHolder.setRoute(channel, routeId);
    }

    @Override
    public void pushAllUserMessage(String message) {

    }

    @Override
    public int getAllUser() {
        return ChannelSocketHolder.getUserCount();
    }

    private String getKey(String userId) {
        return userServerPrefix + userId;
    }

    @Override
    public void userConn(String userId) {
        redisTemplate.boundValueOps(getKey(userId)).set(chatServerId);
    }

    @Override
    public void userOffline(String userId) {
        redisTemplate.delete(getKey(userId));
    }

    @Override
    public void sendMsg(String fromId, String toId, String msg) {
        // 消息处理有两个流程
        // 一、保存到消息数据库, 并且标记为未签收
        String msgId = String.valueOf(idWorker.nextId());
        chatMsgRepository.save(ChatMsg.of(msgId, fromId, toId, msg));

        // 二、如果用户在线推送给用户
        NioSocketChannel channel = ChannelSocketHolder.get(toId);
        if (channel == null) {
            // 用户离线或用户没有注册到本服务器，通过route转发
            ForwardMessage forwardMessage = new ForwardMessage(fromId, toId, msg, msgId);
            routeFeign.sendMessage(forwardMessage);
            return;
        }
        channel.writeAndFlush(ChatMessageFactory.ofSendChatMessage(ChatMessage.MsgActionEnum.CHAT, fromId, toId, msg, msgId));
    }

    @Override
    public void receiveMsg(String msgId) {
        ArrayList<String> list = new ArrayList<>();
        list.add(msgId);
        chatMsgRepository.updateMsgReceived(list);
    }

    @Override
    public void forwardMsg(ForwardMessage message) {
        NioSocketChannel channel = ChannelSocketHolder.get(message.getToUserId());
        if (channel != null) {
            channel.writeAndFlush(ChatMessageFactory.ofSendChatMessage(ChatMessage.MsgActionEnum.CHAT,
                    message.getUserId(), message.getToUserId(), message.getData(), message.getMsgId()));
        }
    }
}
