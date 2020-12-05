package com.sugar.chat.schedule;

import com.sugar.chat.netty.ChatServer;
import com.sugar.chat.util.ChannelSocketHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author LEOSNOW
 */
@Slf4j
@Configuration
@EnableScheduling
public class ChatScheduleTask {
	@Autowired
	private ChatServer chatServer;
	
	@Scheduled(cron = "0 0/1 * * * *")
	private void checkChatServerTask() {
		log.info("check chat server....");
		if (ChannelSocketHolder.getRoute() == null || !ChannelSocketHolder.getRoute().isActive()) {
			chatServer.registerToRoute();
		}
	}
}
