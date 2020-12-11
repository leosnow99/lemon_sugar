package com.sugar.route.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.TransferMessage;
import com.sugar.route.pojo.ChatServerInfo;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 管理ChatServer
 *
 * @author LEOSNOW
 */
@Component
public class ChatServerHandler {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Value("${chat-server.redis.group-name}")
	private String chatServerGroupName;
	
	
	private static BoundZSetOperations<String, String> chatServerTemplate;

//	private static ChatServerHandler chatServerHandlerUtils;
	
	
	@PostConstruct
	private void init() {
//		chatServerHandlerUtils = this;
		if (StringUtils.isEmpty(this.chatServerGroupName)) {
			throw new RuntimeException("参数错误");
		}
		chatServerTemplate = this.redisTemplate.boundZSetOps(chatServerGroupName);
	}
	
	
	private static String getKey(ChatServerInfo chatServerInfo) {
		if (chatServerInfo == null) {
			throw new RuntimeException("参数错误!");
		}
		return chatServerInfo.getId();
	}
	
	public static void chatServerRegister(ChatServerInfo chatServerInfo) {
		String key = getKey(chatServerInfo);
		chatServerTemplate.add(key, 0);
	}
	
	public static void chatServerUpdate(ChatServerInfo chatServerInfo, int count) {
		if (count < 0) {
			return;
		}
		String key = getKey(chatServerInfo);
		chatServerTemplate.add(key, count);
	}
	
	public static void chatServerLogout(ChatServerInfo chatServerInfo) {
		final String key = getKey(chatServerInfo);
		chatServerTemplate.remove(key);
	}
	
	public static ChatServerInfo getChatServer() {
		final Set<String> key = chatServerTemplate.range(0, 0);
		if (key == null) {
			return null;
		}
		return ChatServerHolder.getChatServerInfo((String) key.toArray()[0]);
	}
	
	public static List<ChatServerInfo> getAllChatServer() {
		final Set<String> range = chatServerTemplate.range(0, -1);
		if (range == null) {
			return null;
		}
		final ArrayList<ChatServerInfo> chatServerInfos = new ArrayList<>();
		for (String chatServerId : range) {
			chatServerInfos.add(ChatServerHolder.getChatServerInfo(chatServerId));
		}
		return chatServerInfos;
	}
	
	public static void writeMsg(ChatServerInfo chatServerInfo, ChatMessage.Message chatMessage) {
		final NioSocketChannel channel = ChatServerHolder.getChannel(chatServerInfo);
		if (channel != null) {
			channel.writeAndFlush(TransferMessage.of(chatMessage));
		}
	}
	
	public static void writeMsgToAll(ChatMessage.Message message) {
		ChatServerHolder.getAllChannel().forEach(channel -> channel.writeAndFlush(message));
	}
}
