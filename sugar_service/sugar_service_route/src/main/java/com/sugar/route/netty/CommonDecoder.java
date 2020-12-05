package com.sugar.route.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.TransferMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author LEOSNOW
 */
public class CommonDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		if (msg.readableBytes() < 4) {
			return;
		}
		//在读取前标记readerIndex
		msg.markReaderIndex();
		
		final int length = msg.readInt();
		if (msg.readableBytes() < length) {
			//消息不完整，无法处理，将readerIndex复位
			msg.resetReaderIndex();
			return;
		}
		final byte[] bytes = new byte[length];
		msg.readBytes(bytes);
		
		final ChatMessage.Message message = ChatMessage.Message.parseFrom(bytes);
		final TransferMessage transferMessage = new TransferMessage();
		transferMessage.setLength(length);
		transferMessage.setMessage(message);
		out.add(transferMessage);
	}
}
