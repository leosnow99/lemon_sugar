package com.sugar.route.dao;

import com.sugar.route.pojo.ChatServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author bytedance
 */
@Slf4j
@Repository
public class ChatServerDao {
    @Value("${chat-server.redis.group-name}")
    private String chatServerPrefix;

    @Resource
    private RedisTemplate<String, ChatServerInfo> redisTemplate;


    public String getKey(String chatServerId) {
        return chatServerPrefix + "_" + chatServerId;
    }

    public ChatServerInfo getChatServerByChatServerId(String chatServerId) {
        String key = getKey(chatServerId);
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 将 chatServer count 数量存储到 redis 中
     * @param chatServerInfo chatServer详细信息
     */
    public void setCountByChatServerId(ChatServerInfo chatServerInfo) {
        String key = getKey(chatServerInfo.getId());
        redisTemplate.boundValueOps(key).set(chatServerInfo);
        redisTemplate.boundValueOps(key).expire(4, TimeUnit.SECONDS);
    }

    public void deleteChatServer(String chatServerId) {
        redisTemplate.delete(getKey(chatServerId));
    }
}
