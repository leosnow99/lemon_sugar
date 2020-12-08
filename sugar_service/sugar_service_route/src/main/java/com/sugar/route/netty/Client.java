package com.sugar.route.netty;

import com.sugar.route.pojo.ChatServerInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author LEOSNOW
 */
@Slf4j
public class Client {
	public static void connectChatServer(ChatServerInfo chatServerInfo) {
		try {
			final NioEventLoopGroup workGroup = new NioEventLoopGroup();
			final Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(workGroup).channel(NioSocketChannel.class).handler(new ClientInitializer());
			
			if (chatServerInfo == null || StringUtils.isEmpty(chatServerInfo.getAddress()) || chatServerInfo.getPort() == null) {
				return;
			}
			
			final ChannelFuture future = bootstrap.connect(chatServerInfo.getAddress(), chatServerInfo.getPort()).sync();
			if (future.isSuccess()) {
				String clientId = chatServerInfo.getId();
				ChatServerHolder.CHAT_SERVER_INFO.put(clientId, chatServerInfo);
				ChatServerHolder.GROUPS.put(clientId, workGroup);
				ChatServerHolder.CLIENTS.put(clientId, ((NioSocketChannel) future.channel()));
				ChatServerHolder.SESSION_CLIENTS.put(((NioSocketChannel) future.channel()), clientId);
			}
		} catch (InterruptedException e) {
			log.error("异常: ", e);
		}
	}
	
	
}
