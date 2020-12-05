package com.sugar.chat.service.impl;

import com.sugar.chat.service.ChatService;
import com.sugar.chat.util.ChannelSocketHolder;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author LEOSNOW
 */
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private DiscoveryClient discoveryClient;
	
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
		ChannelSocketHolder.setRoute(channel, routeId);
	}
	
	@Override
	public void pushAllUserMessage(String message) {
	
	}
	
	@Override
	public int getAllUser() {
		return ChannelSocketHolder.getUserCount();
	}
	
	
}
