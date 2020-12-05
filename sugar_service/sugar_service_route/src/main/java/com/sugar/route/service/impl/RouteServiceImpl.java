package com.sugar.route.service.impl;

import com.sugar.route.netty.Client;
import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.UserChatInfo;
import com.sugar.route.service.RouteService;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * @author LEOSNOW
 */
@Service
public class RouteServiceImpl implements RouteService {
	@Override
	public void register( ChatServerInfo chatServerInfo) {
		Client.connectChatServer(chatServerInfo);
	}
	
	@Override
	public ChatServerInfo getChatServer(UserChatInfo userChatInfo) {
		return null;
	}
	
	@Override
	public void shutDownServer(ChatServerInfo chatServerInfo) {
	
	}
	
	@Override
	public Long getCountUserServer(ChatServerInfo chatServerInfo) {
		return null;
	}
	
	@Override
	public void shutdownUser(String userId) {
	
	}
	
	@Override
	public void pushAllUserMessage(String message) {
	
	}
	
	@PreDestroy
	public void destroy() {
		Client.destroy();
	}
}
