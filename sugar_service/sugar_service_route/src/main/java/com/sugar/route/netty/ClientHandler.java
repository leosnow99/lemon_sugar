package com.sugar.route.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.pojo.TransferMessage;
import com.sugar.route.pojo.ChatServerInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LEOSNOW
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<TransferMessage> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TransferMessage msg) {
		if (msg.getMessage() != null && msg.getMessage().getDataType() == ChatMessage.Message.DataType.RouteMsgType) {
			final String data = msg.getMessage().getRouteMsg().getData();
			handlerChatServerHasCount(ctx, data);
		}
	}
	
	public void handlerChatServerHasCount(ChannelHandlerContext ctx, String numbers) {
		final ChatServerInfo chatServerInfo = ChatServerHolder.getChatServerInfo((NioSocketChannel) ctx.channel());
		if (chatServerInfo != null) {
			ChatServerHandler.chatServerUpdate(chatServerInfo, Integer.parseInt(numbers));
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.REGISTER)));
		ctx.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER)));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		final ChatServerInfo chatServerInfo = ChatServerHolder.getChatServerInfo((NioSocketChannel) ctx.channel());
		if (chatServerInfo != null) {
			ChatServerHandler.chatServerLogout(chatServerInfo);
		}
		ctx.channel().close();
		super.exceptionCaught(ctx, cause);
	}
}
