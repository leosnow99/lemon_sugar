package com.sugar.chat.util;

import com.surgar.entity.CommonCode;
import com.surgar.exception.ExceptionCast;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户socket管理器
 *
 * @author LEOSNOW
 */
public class ChannelSocketHolder {
	private static final Map<String, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);
	
	public static void put(String userId, NioSocketChannel socketChannel) {
		CHANNEL_MAP.put(userId, socketChannel);
	}
	
	public static NioSocketChannel get(String userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return CHANNEL_MAP.get(userId);
	}
	
	/**
	 * @return UserID 与 channel的映射表
	 */
	public static Map<String, NioSocketChannel> getRelationShip() {
		return CHANNEL_MAP;
	}
	
	public static String getUserId(NioSocketChannel socketChannel) {
		if (socketChannel == null) {
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}
		
		for (String key : CHANNEL_MAP.keySet()) {
			if (CHANNEL_MAP.get(key) == socketChannel) {
				return key;
			}
		}
		return null;
	}
	
	public static void remove(NioSocketChannel socketChannel) {
		if (socketChannel == null) {
			ExceptionCast.cast(CommonCode.INVALID_PARAM);
		}
		CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == socketChannel).forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
		
	}
	
	public static void closeUser(String userId) {
		final NioSocketChannel channel = get(userId);
		if (channel != null) {
			channel.close();
			remove(channel);
		}
	}
	
	public static void closeAllUser() {
		CHANNEL_MAP.entrySet().stream().filter(entry -> !entry.getKey().startsWith("route")).forEach(entry -> {
			entry.getValue().close();
			remove(entry.getValue());
		});
	}
	
	public static int getUserCount() {
//		return CHANNEL_MAP.entrySet().stream().filter(entry -> !entry.getKey().startsWith("route_")).toArray().length;
		return CHANNEL_MAP.size();
	}
}
