package com.sugar.chat.netty;

import com.sugar.chat.pojo.TransferMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author LEOSNOW
 */
public class CommonEncoder extends MessageToByteEncoder<TransferMessage> {
	@Override
	protected void encode(ChannelHandlerContext ctx, TransferMessage msg, ByteBuf out) {
		out.writeLong(msg.getLength());
		out.writeBytes(msg.getMessage().toByteArray());
	}
}
