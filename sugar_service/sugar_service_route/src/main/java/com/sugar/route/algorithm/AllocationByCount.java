package com.sugar.route.algorithm;

import com.sugar.route.netty.ChatServerHandler;
import com.sugar.route.pojo.ChatServerInfo;
import org.springframework.stereotype.Component;

/**
 * 根据客户端数量分配
 *
 * @author LEOSNOW
 */
@Component
public class AllocationByCount implements AllocationAlgorithm {
	@Override
	public ChatServerInfo getServer() {
		return ChatServerHandler.getChatServer();
	}
}
