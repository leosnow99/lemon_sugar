package com.sugar.route.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class ServerHandler extends SimpleChannelInboundHandler<String> {
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) {
		System.out.println("--------");
		System.out.println(msg);
		ctx.writeAndFlush("I have receive you message");
		System.out.println("--------");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.channel().close();
	}
	
//	@Override
//	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//		System.out.println("complete");
//		ctx.channel().writeAndFlush("complete ok!");
//	}
}
