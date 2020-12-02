package com.sugar.route.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author LEOSNOW
 */
@Component
@Slf4j
public class Client {
	
	private final EventLoopGroup workGroup = new NioEventLoopGroup();
	
	
	@Value("${route.address}")
	private String address;
	
	@Value("${route.port}")
	private Integer port;
	
	@PostConstruct
	public void start() {
		try{
			final Bootstrap bootstrap = new Bootstrap();
			
			bootstrap.group(workGroup)
					.channel(NioSocketChannel.class)
					.handler(null);
			log.info("Router Server 启动成功!");
			
			//启动客户端连接服务器端
			bootstrap.connect(address, port).sync();
			
		} catch (InterruptedException e) {
			log.error("异常: ", e);
			e.printStackTrace();
		}
	}
	
}
