package com.sugar.route.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.TransferMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LEOSNOW
 */
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<TransferMessage> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TransferMessage msg) {
		if (msg.getMessage() != null && msg.getMessage().getDataType() == ChatMessage.Message.DataType.RouteMsgType) {
			final String numbers = msg.getMessage().getRouteMsg().getData();
			log.info("Numbers: " + numbers);
		}
	}
}
