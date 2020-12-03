package com.sugar.chat.schedule;

import com.sugar.chat.netty.ChatServer;
import lombok.extern.slf4j.Slf4j;
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
	
	@Scheduled(cron = "* 0/1 * * * *")
	private void checkChatServerTask() {
		log.info("check chat server....");
	}
}
