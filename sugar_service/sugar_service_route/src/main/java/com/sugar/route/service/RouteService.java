package com.sugar.route.service;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.UserChatInfo;

/**
 * @author LEOSNOW
 */
public interface RouteService {
	
	/**
	 * 服务器上线, 注册
	 *
	 * @param chatServerInfo 服务器信息
	 */
	void register(ChatServerInfo chatServerInfo);
	
	
	/**
	 * 请求一个服务
	 *
	 * @param userChatInfo 用户信息
	 * @return 返回一个服务器实例
	 */
	ChatServerInfo getChatServer(UserChatInfo userChatInfo);
	
	
	/**
	 * 下线一个服务器
	 *
	 * @param chatServerInfo 服务器信息
	 */
	void shutDownServer(ChatServerInfo chatServerInfo);
	
	/**
	 * 获取服务器注册用户
	 *
	 * @param chatServerInfo 服务器信息
	 * @return 注册用户数量
	 */
	Long getCountUserServer(ChatServerInfo chatServerInfo);
	
	
	/**
	 * 下线用户
	 *
	 * @param userId 下线用户ID
	 */
	void shutdownUser(String userId);
	
	/**
	 * 向所有用户推送消息
	 *
	 * @param message 消息内容
	 */
	void pushAllUserMessage(String message);
}
