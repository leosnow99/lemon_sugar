package com.sugar.chat.pojo;

import lombok.Data;

import javax.xml.transform.Transformer;

/**
 * @author LEOSNOW
 */
@Data
public class TransferMessage {
	int length;
	ChatMessage.Message message;
	
	public static TransferMessage of(ChatMessage.Message message) {
		final TransferMessage transferMessage = new TransferMessage();
		transferMessage.setLength(message.toByteArray().length);
		transferMessage.setMessage(message);
		return transferMessage;
	}
}
