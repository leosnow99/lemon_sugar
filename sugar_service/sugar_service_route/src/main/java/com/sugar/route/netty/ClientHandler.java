package com.sugar.route.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.pojo.TransferMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author LEOSNOW
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<TransferMessage> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TransferMessage msg) {
		if (msg.getMessage() != null && msg.getMessage().getDataType() == ChatMessage.Message.DataType.RouteMsgType) {
			final String numbers = msg.getMessage().getRouteMsg().getData();
			handlerChatServerHasCount(((NioSocketChannel) ctx.channel()), numbers);
		}
	}
	
	public void handlerChatServerHasCount(NioSocketChannel channel, String numbers) {
		final Map<NioSocketChannel, String> clients = Client.getClients();
		System.out.println("id: " + clients.get(channel) + " numbers: " + numbers);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.REGISTER)));
		ctx.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER)));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		super.exceptionCaught(ctx, cause);
	}
}
