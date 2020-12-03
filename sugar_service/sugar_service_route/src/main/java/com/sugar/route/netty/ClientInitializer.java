package com.sugar.route.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author LEOSNOW
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
	
	@Override
	protected void initChannel(SocketChannel ch) {
		final ChannelPipeline pipeline = ch.pipeline();
		
		pipeline.addLast(new CommonDecoder());
		pipeline.addLast(new CommonEncoder());
		pipeline.addLast(new ClientHandler());
	}
}
