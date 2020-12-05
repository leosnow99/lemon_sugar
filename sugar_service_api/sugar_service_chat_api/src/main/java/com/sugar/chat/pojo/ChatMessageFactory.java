package com.sugar.chat.pojo;

import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;

/**
 * @author LEOSNOW
 */
public class ChatMessageFactory {
	public static ChatMessage.Message ofRouteMessage(ChatMessage.RouteMsgActionEnum msgAction, String data) {
		return  ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.RouteMsgType)
				.setRouteMsg(ChatMessage.RouteMsg.newBuilder().setMsgAction(msgAction).setData(data)).build();
	}
	
	public static ChatMessage.Message ofRouteMessage(ChatMessage.RouteMsgActionEnum msgAction) {
		return  ChatMessage.Message.newBuilder().setDataType(ChatMessage.Message.DataType.RouteMsgType)
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
}
