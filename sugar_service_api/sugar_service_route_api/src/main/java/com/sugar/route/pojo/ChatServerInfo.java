package com.sugar.route.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LEOSNOW
 */
@Data
@AllArgsConstructor
public class ChatServerInfo {
	private String id;
	private String address;
	private Integer port;
	
	public static ChatServerInfo of(String id, String address, Integer port) {
		return new ChatServerInfo(id, address, port);
	}
}
