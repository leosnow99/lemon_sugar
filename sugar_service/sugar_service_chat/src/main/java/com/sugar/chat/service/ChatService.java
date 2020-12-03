package com.sugar.chat.service;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author LEOSNOW
 */
public interface ChatService {
	
	
	/**
	 * 下线所有用户
	 */
	void shutdownAllUser();
	
	/**
	 *下线指定用户
	 * @param userId 用户id
	 */
	void shutdownUser(String userId);
	
	
	/**
	 * 注册路由器
	 * @param routeId 路由id
	 * @param channel NioSocketChannel
	 */
	void registerRoute(String routeId, NioSocketChannel channel);
	
	/**
	 * 给所有用户推送消息
	 * @param message 消息体
	 */
	void pushAllUserMessage(String message);
	
	/**
	 * 返回用户数量
	 * @return user数量
	 */
	int getAllUser();
}
