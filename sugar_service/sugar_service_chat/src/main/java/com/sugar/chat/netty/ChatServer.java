package com.sugar.chat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author LEOSNOW
 */
@Slf4j
@Component
public class ChatServer {
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	private static ChannelFuture future;
	
	@Value("${nettyService.port}")
	private int port;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@PostConstruct
	public void start() {
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ServerInitializer());
			
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
	
	private void registerToRoute() {
		final List<ServiceInstance> instances = discoveryClient.getInstances("CHAT-ROUTE");
		if (instances == null || instances.isEmpty()) {
			log.warn("no route is discover...");
			return;
		}
		final Random random = new Random();
		ServiceInstance instance = instances.get(random.nextInt(instances.size()));
		final Map<String, String> metadata = instance.getMetadata();
		for (Map.Entry<String, String> entry : metadata.entrySet()) {
			System.out.println("key: " + entry.getKey());
			System.out.println("value: " + entry.getValue());
			//TODO 选择注册 路由节点
		}
	}
	
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		log.info("netty server close!!!");
	}
}
