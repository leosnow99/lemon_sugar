package com.sugar.route.algorithm;

import com.sugar.route.netty.ChatServerHandler;
import com.sugar.route.pojo.ChatServerInfo;
import org.apache.catalina.util.ServerInfo;

/**
 * 轮询实现
 * @author LEOSNOW
 */
public class PollingAllocation implements AllocationAlgorithm{
	@Override
	public ChatServerInfo getServer() {
		return ChatServerHandler.getChatServer();
	}
}
