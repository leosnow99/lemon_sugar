package com.sugar.route.algorithm;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.util.ChatServiceHandle;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author LEOSNOW
 */
@Component
public class RandomAllocation implements AllocationAlgorithm {
    @Override
    public ChatServerInfo getServer() {
        List<ChatServerInfo> serverInfos = ChatServiceHandle.getAllChatServer();

        final Random random = new Random();
        return serverInfos.get(random.nextInt(serverInfos.size()));
    }
}
