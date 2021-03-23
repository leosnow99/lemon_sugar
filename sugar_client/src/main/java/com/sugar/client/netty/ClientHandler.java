package com.sugar.client.netty;

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
        if (msg.getMessage() != null && msg.getMessage().getDataType() == ChatMessage.Message.DataType.ChatMsgType) {
            ChatMessage.ChatMsg chatMsg = msg.getMessage().getChatMsg();
            System.out.println("收到：" + chatMsg.getUserId() + " 的消息：" + chatMsg.getData());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 发送注册到 IM 服务器信号
        System.out.println("注册到服务器。。。");
        ChatMessage.Message message = ChatMessage.Message.newBuilder()
                .setDataType(ChatMessage.Message.DataType.ChatMsgType)
                .setChatMsg(ChatMessage.ChatMsg.newBuilder()
                        .setUserId(Client.id)
                        .setMsgAction(ChatMessage.MsgActionEnum.CONNECT))
                .build();
        ctx.writeAndFlush(TransferMessage.of(message));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务链接异常尝试重新链接");
        super.exceptionCaught(ctx, cause);
    }
}
