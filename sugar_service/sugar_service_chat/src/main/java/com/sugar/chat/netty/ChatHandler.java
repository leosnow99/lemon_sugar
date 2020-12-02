package com.sugar.chat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

//TextWebSocketFrame: 在netty中, 是用于为websocket 专门处理文本的对象, frame是消息到载体
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
	
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		//TODO hash表移除channel
		ctx.close();
	}
}
