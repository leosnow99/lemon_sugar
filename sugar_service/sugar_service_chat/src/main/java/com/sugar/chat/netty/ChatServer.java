package com.sugar.chat.netty;

import com.sugar.route.feign.RouteFeign;
import com.sugar.route.pojo.ChatServerInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author LEOSNOW
 */
@Slf4j
@Component
public class ChatServer {
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	private static ChannelFuture future;
	
	@Autowired
	private RouteFeign routeFeign;
	
	@Value("${netty-service.ip}")
	private String ip;
	
	@Value("${netty-service.port}")
	private int port;
	
	@Value("${spring.application.name}")
	private String name;
	
	@Value("${netty-service.id}")
	public String serverId;
	
	@PostConstruct
	public void start() {
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChatInitializer());
			
			future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				log.info("service start success!!!");
				registerToRoute();
			}
		} catch (InterruptedException e) {
			log.error("service start error!!!");
			e.printStackTrace();
		}
	}
	
	public void registerToRoute() {
		routeFeign.register(ChatServerInfo.of(name + ":" + serverId, ip, port));
	}
	
	
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		log.info("netty server close!!!");
	}
}
