package com.sugar.chat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
@Slf4j
public class ChatServer {
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	
	@Value("${nettyService.port}")
	private int port;
	
	@PostConstruct
	public void start() {
		try {
			final ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer());
			final ChannelFuture future;
			future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				log.info("service start success!!!");
			}
		} catch (InterruptedException e) {
			log.error("service start error!!!");
			e.printStackTrace();
		}
	}
	
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		log.info("netty server close!!!");
	}
}
