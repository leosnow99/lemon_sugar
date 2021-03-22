package com.sugar.route.schedule;

import com.sugar.route.util.ChatServiceHandle;
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
public class RouteSchedule {
//	@Scheduled(cron = "0/30 * * * * *")
//	public void checkChatService() {
//		log.info("checkChatService");
//		final Map<NioSocketChannel, String> clients = ChatServerHolder.getClients();
//		for (NioSocketChannel channel : clients.keySet()) {
//			if (channel.isActive()) {
//				log.info("active...");
//				channel.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER)));
//			} else {
//				clients.remove(channel);
//			}
//		}
//	}

	@Autowired
	private ChatServiceHandle chatServiceHandle;

	@Scheduled(cron = "0/30 * * * * *")
	public void checkChatService() {
		log.info("checkChatService");

		ChatServiceHandle.getAllChatServer().forEach(e -> chatServiceHandle.check(e));
	}
}
