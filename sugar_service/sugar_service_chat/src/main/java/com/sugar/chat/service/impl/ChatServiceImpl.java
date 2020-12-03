package com.sugar.chat.service.impl;

import com.sugar.chat.service.ChatService;
import com.sugar.chat.util.ChannelSocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Service;

/**
 * @author LEOSNOW
 */
@Service
public class ChatServiceImpl implements ChatService {
	@Override
	public void shutdownAllUser() {
		ChannelSocketHolder.closeAllUser();
	}
	
	@Override
	public void shutdownUser(String userId) {
		ChannelSocketHolder.closeUser(userId);
	}
	
	@Override
	public void registerRoute(String routeId, NioSocketChannel channel) {
		routeId = "route_" + routeId;
		ChannelSocketHolder.put(routeId, channel);
	}
	
	@Override
	public void pushAllUserMessage(String message) {
	
	}
	
	@Override
	public int getAllUser() {
		return ChannelSocketHolder.getUserCount();
	}
}
