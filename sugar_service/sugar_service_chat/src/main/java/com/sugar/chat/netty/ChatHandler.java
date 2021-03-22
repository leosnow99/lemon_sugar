package com.sugar.chat.netty;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.pojo.TransferMessage;
import com.sugar.chat.service.ChatService;
import com.sugar.chat.util.ChannelSocketHolder;
import com.sugar.chat.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * @author LEOSNOW
 * @TextWebSocketFrame: 在netty中, 是用于为websocket 专门处理文本的对象, frame是消息到载体
 * @see io.netty.channel.SimpleChannelInboundHandler  消息处理器
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TransferMessage> {
    private final ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferMessage msg) {
        if (msg == null) {
            log.warn("channelRead0: msg is null");
            return;
        }
        final ChatMessage.Message message = msg.getMessage();
        if (message.getDataType() == ChatMessage.Message.DataType.ChatMsgType) {
            handleChatMessage(message.getChatMsg(), (NioSocketChannel) ctx.channel());
        } else if (message.getDataType() == ChatMessage.Message.DataType.RouteMsgType) {
            handleRoutMessage(message.getRouteMsg(), (NioSocketChannel) ctx.channel());
        }

    }

    private void handleChatMessage(ChatMessage.ChatMsg chatMsg, NioSocketChannel channel) {
        String userId = chatMsg.getUserId();

        switch (chatMsg.getMsgAction()) {
            case CONNECT:
                chatService.userConn(userId);
                ChannelSocketHolder.put(userId, channel);
                log.info("user: " + userId + " conn");
                break;
            case CHAT:
                String toUserId = chatMsg.getToUserId();
                log.info("receive user: " + userId + " send to: " + toUserId);

                chatService.sendMsg(userId, toUserId, chatMsg.getData());
                break;
            case SIGNED:
                chatService.receiveMsg(chatMsg.getMsgId());
                log.info("receive msg id: " + chatMsg.getMsgId());
                break;
            default:
                log.warn("other chat msg type: " + chatMsg.getMsgAction());
        }
    }

    private void handleRoutMessage(ChatMessage.RouteMsg routeMsg, NioSocketChannel channel) {
        log.info("receive route message!");
        if (routeMsg == null) {
            return;
        }
        switch (routeMsg.getMsgAction()) {
            case ALL_USER:
                log.info("收到: ALL_USER");
                final int count = chatService.getAllUser();
                log.info("count: [{}]", count);
                final ChatMessage.Message message = ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER,
                        String.valueOf(count));
                channel.writeAndFlush(TransferMessage.of(message));
                break;
            case REGISTER:
                System.out.println("register-----");
                chatService.registerRoute(routeMsg.getData(), channel);
                break;
            case SHUTDOWN_USER:
                chatService.shutdownUser(routeMsg.getData());
                break;
            case PUSH_ALL_USER_MESSAGE:
                chatService.pushAllUserMessage(routeMsg.getData());
                break;
            default:
                log.warn("error type!");
        }
    }

    /**
     * 下线用户
     *
     * @param ctx 控制器
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        final String userId = ChannelSocketHolder.getUserId(((NioSocketChannel) ctx.channel()));
        if (userId != null) {
            log.warn("[{}] is offline! ", userId);
            ChannelSocketHolder.remove(((NioSocketChannel) ctx.channel()));
        }
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String userId = ChannelSocketHolder.getUserId((NioSocketChannel) ctx.channel());
        chatService.userOffline(userId);

        ChannelSocketHolder.remove((NioSocketChannel) ctx.channel());
        ctx.close();
        log.error("exceptionCaught: ", cause);
    }
}
