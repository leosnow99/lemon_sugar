package com.sugar.route.netty;

import com.sugar.route.pojo.ChatServerInfo;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * IM服务器管理器
 *
 * @author LEOSNOW
 */
public class ChatServerHolder {
	
	public static final Map<String, EventLoopGroup> GROUPS = new HashMap<>();
	
	public static final Map<String, NioSocketChannel> CLIENTS = new HashMap<>();
	
	public static final Map<String, ChatServerInfo> CHAT_SERVER_INFO = new HashMap<>();
	
	public static final Map<NioSocketChannel, String> SESSION_CLIENTS = new HashMap<>();
	
	public static Map<NioSocketChannel, String> getClients() {
		return SESSION_CLIENTS;
	}
	
	public static ChatServerInfo getChatServerInfo(NioSocketChannel channel) {
		final String serverId = SESSION_CLIENTS.get(channel);
		if (serverId == null) {
			return null;
		}
		return CHAT_SERVER_INFO.get(serverId);
	}
	
	public static ChatServerInfo getChatServerInfo(String serverId ) {
		return CHAT_SERVER_INFO.get(serverId);
	}
	
	public static NioSocketChannel getChannel(ChatServerInfo chatServerInfo) {
		if (chatServerInfo == null) {
			return null;
		}
		return CLIENTS.get(chatServerInfo.getId());
		
	}
	
	public static Collection<NioSocketChannel> getAllChannel() {
		return CLIENTS.values();
	}
	
	public static void destroy() {
		for (NioSocketChannel channel : ChatServerHolder.CLIENTS.values()) {
			channel.close();
		}
		
		for (EventLoopGroup loopGroup : ChatServerHolder.GROUPS.values()) {
			loopGroup.shutdownGracefully();
		}
	}
}
