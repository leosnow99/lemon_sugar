package com.sugar.route.algorithm;

import com.sugar.route.netty.ChatServerHandler;
import com.sugar.route.pojo.ChatServerInfo;

import java.util.List;
import java.util.Random;

/**
 * @author LEOSNOW
 */
public class RandomAllocation implements AllocationAlgorithm {
	@Override
	public ChatServerInfo getServer() {
		final List<ChatServerInfo> serverInfos = ChatServerHandler.getAllChatServer();
		if (serverInfos == null) {
			return null;
		}
		final Random random = new Random();
		return serverInfos.get(random.nextInt(serverInfos.size()));
	}
}
