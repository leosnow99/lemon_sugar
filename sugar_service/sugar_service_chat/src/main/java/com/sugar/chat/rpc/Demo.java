package com.sugar.chat.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

/**
 * @author LEOSNOW
 */
//@Component
@Slf4j
public class Demo {
	private final NioEventLoopGroup loopGroup = new NioEventLoopGroup(1);
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	private ChannelFuture future;
	private final Bootstrap bootstrap = new Bootstrap();
	
	private String sentinelPort;
	private String sentinelIp;
	
	
	@PostConstruct
	public void start() {
		final List<ServiceInstance> instances = discoveryClient.getInstances("IMSENTINEL");
		final ServiceInstance serviceInstance = instances.get(0);
		final Map<String, String> metadata = serviceInstance.getMetadata();
		sentinelIp = metadata.get("sentinelIP");
		sentinelPort = metadata.get("sentinelPort");
		
		log.info("sentinelIP: " + sentinelIp);
		log.info("sentinelPort: " + sentinelPort);
		connect();
		heart();
	}
	
	private void connect() {
		try {
			bootstrap.group(loopGroup)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) {
							final ChannelPipeline pipeline = socketChannel.pipeline();
							//向pipeline加入解码器
							pipeline.addLast("decoder", new StringDecoder());
							//向pipeline加入编码器
							pipeline.addLast("encoder", new StringEncoder());
							pipeline.addLast(new DemoHandler());
						}
					});
			future = bootstrap.connect(sentinelIp, Integer.parseInt(sentinelPort)).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void heart() {
		new Thread(() -> {
			try {
				while (true) {
					if (future.channel().isActive()) {
						System.out.println("进入心跳线程！");
						future.channel().writeAndFlush("我心跳啦！！！");
						Thread.sleep(30000);
					} else {
						connect();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	
	@PreDestroy
	public void destroy() {
		loopGroup.shutdownGracefully();
	}
	
}
