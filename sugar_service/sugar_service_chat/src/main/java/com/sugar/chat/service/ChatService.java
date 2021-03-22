package com.sugar.chat.service;

import com.sugar.route.pojo.ForwardMessage;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author LEOSNOW
 */
public interface ChatService {


	/**
	 * 下线所有用户
	 */
	void shutdownAllUser();

	/**
	 *下线指定用户
	 * @param userId 用户id
	 */
	void shutdownUser(String userId);


	/**
	 * 注册路由器
	 * @param routeId 路由id
	 * @param channel NioSocketChannel
	 */
	void registerRoute(String routeId, NioSocketChannel channel);

	/**
	 * 给所有用户推送消息
	 * @param message 消息体
	 */
	void pushAllUserMessage(String message);

	/**
	 * 返回用户数量
	 * @return user数量
	 */
	int getAllUser();


    /**
     * 处理用户连接状态落库请求
     * @param userId 用户id
     */
	void userConn(String userId);

    /**
     * 处理用户离线
     * @param userId 离线用户id
     */
    void userOffline(String userId);

    /**
     * 处理用户发送消息
     * @param fromId 消息发送者
     * @param toId 消息接收者
     * @param msg 消息类型
     */
	void sendMsg(String fromId, String toId, String msg);

	/**
	 * 消息送达确认
	 * @param msgId 消息id
	 */
	void receiveMsg(String msgId);

	/**
	 * 处理route 转发的消息
	 * @param forwardMessage 转发消息载体
	 */
	void forwardMsg(ForwardMessage forwardMessage);
}
