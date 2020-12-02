package com.sugar.route.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
//@Component
public class Server {
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
//	@Value("${nettyService.sentinel.prefix}")
//	private String prefix;
//
//	@Value("${nettyService.ip}")
//	private String ip;
	
	@Value("${nettyService.port}")
	private int port;
	
//	private String key;
	
	@PostConstruct
	public void start() {
		try {
			final ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ServerInitializer());
			final ChannelFuture future = bootstrap.bind(port).sync();
			if (future.isSuccess()) {
				log.info("service start success!!!");
			}
		} catch (InterruptedException e) {
			log.error("service start error!!!");
			e.printStackTrace();
		}
		
		
		
	}
	
//	//将哨兵注册到redis中
//	public void registerToRedis() {
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.opsForValue().set(key, String.valueOf(port));
//		redisTemplate.expire(key, 60, TimeUnit.SECONDS);
//	}
	
	@PreDestroy
	public void destroy() {
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		log.info("netty server close!!!");
	}
}
