package com.sugar.chat.pojo;

/**
 * @author LEOSNOW
 */
public class ChatMessageFactory {
    public static ChatMessage.Message ofRouteMessage(ChatMessage.RouteMsgActionEnum msgAction, String data) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.RouteMsgType)
                .setRouteMsg(ChatMessage.RouteMsg.newBuilder().setMsgAction(msgAction).setData(data)).build();
    }

    public static ChatMessage.Message ofRouteMessage(ChatMessage.RouteMsgActionEnum msgAction) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.RouteMsgType)
                .setRouteMsg(ChatMessage.RouteMsg.newBuilder().setMsgAction(msgAction)).build();
    }

    public static ChatMessage.Message ofChatMessage(ChatMessage.MsgActionEnum msgAction) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.ChatMsgType)
                .setChatMsg(ChatMessage.ChatMsg.newBuilder().setMsgAction(msgAction)).build();

    }

    public static ChatMessage.Message ofChatMessage(ChatMessage.MsgActionEnum msgAction, String data) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.ChatMsgType)
                .setChatMsg(ChatMessage.ChatMsg.newBuilder().setMsgAction(msgAction).setData(data)).build();

    }

    public static ChatMessage.Message ofSendChatMessage(ChatMessage.MsgActionEnum msgAction, String fromId, String toId, String data, String msgId) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.ChatMsgType)
                .setChatMsg(ChatMessage.ChatMsg.newBuilder().setMsgAction(msgAction).setUserId(fromId)
                        .setToUserId(toId).setData(data).setMsgId(msgId)).build();
    }

    public static ChatMessage.Message ofSendChatMessage(ChatMessage.MsgActionEnum msgAction, String fromId, String toId, String data) {
        return ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.ChatMsgType)
                .setChatMsg(ChatMessage.ChatMsg.newBuilder().setMsgAction(msgAction).setUserId(fromId)
                        .setToUserId(toId).setData(data)).build();
    }
}
