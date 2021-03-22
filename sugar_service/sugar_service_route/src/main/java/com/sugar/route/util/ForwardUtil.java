package com.sugar.route.util;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.ForwardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author bytedance
 */

@Component
@Slf4j
public class ForwardUtil {
    @Value("${chat-server.redis.user_server_prefix}")
    public String prefix;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String getKey(String userId) {
        return prefix + userId;
    }

    public String getServerIdByUserId(String userId) {
        String key = getKey(userId);
        String serverId = redisTemplate.boundValueOps(key).get();

        log.info("[getServerIdByUserId] userID: " + userId + " serverID: " + serverId);
        return serverId;
    }

    public void sendMessage(ChatServerInfo chatServerInfo, ForwardMessage message) {
        String instance = chatServerInfo.getAddress() + ":" + chatServerInfo.getPort();
        WebClient webClient = WebClient.builder().baseUrl(instance).build();
        Mono<String> result = webClient.post()
                .uri("/message")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(String.class);
        result.subscribe(System.err::println);
    }
}
