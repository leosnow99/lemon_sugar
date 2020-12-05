package com.sugar.chat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author LEOSNOW
 */
public class ChatInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		final ChannelPipeline pipeline = socketChannel.pipeline();
		
		pipeline.addLast(new CommonDecoder());
		pipeline.addLast(new CommonEncoder());
		
		//针对客户端, 如果在一分钟没有向服务器端发送读写心跳,则主动断开
		//如果是读空闲或者写空闲, 不处理
		pipeline.addLast(new IdleStateHandler(10, 10, 80));
		//自定义的空闲状态检测
		pipeline.addLast(new HeartBeatHandler());
		
		pipeline.addLast(new ChatHandler());
	}
}
