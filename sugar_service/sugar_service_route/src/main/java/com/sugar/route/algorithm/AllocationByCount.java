package com.sugar.route.algorithm;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.util.ChatServiceHandle;

import java.util.List;

/**
 * 根据客户端数量分配
 *
 * @author LEOSNOW
 */
public class AllocationByCount implements AllocationAlgorithm {
	@Override
	public ChatServerInfo getServer() {
		List<ChatServerInfo> chatServerInfos = ChatServiceHandle.getAllChatServer();
		return chatServerInfos.get(0);
//		return ChatServerHandler.getChatServer();
	}
}
