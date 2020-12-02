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
}
