package com.sugar.chat.schedule;

import com.sugar.chat.netty.ChatServer;
import com.sugar.chat.util.ChannelSocketHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LEOSNOW
 */
@Slf4j
//@Configuration
//@EnableScheduling
public class ChatScheduleTask {
	@Autowired
	private ChatServer chatServer;
	
//	@Scheduled(cron = "0 0/3 * * * *")
	private void checkChatServerTask() {
		log.info("check chat server....");
		if (ChannelSocketHolder.getRoute() == null || !ChannelSocketHolder.getRoute().isActive()) {
			chatServer.registerToRoute();
		}
	}
}
