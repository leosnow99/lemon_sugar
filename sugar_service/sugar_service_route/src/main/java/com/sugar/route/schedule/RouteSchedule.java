package com.sugar.route.schedule;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.pojo.TransferMessage;
import com.sugar.route.netty.Client;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

/**
 * @author LEOSNOW
 */
@Slf4j
@Configuration
@EnableScheduling
public class RouteSchedule {
	@Scheduled(cron = "0/30 * * * * *")
	public void checkChatService() {
		log.info("checkChatService");
		final Map<NioSocketChannel, String> clients = Client.getClients();
		for (NioSocketChannel channel : clients.keySet()) {
			if (channel.isActive()) {
				log.info("active...");
				channel.writeAndFlush(TransferMessage.of(ChatMessageFactory.ofRouteMessage(ChatMessage.RouteMsgActionEnum.ALL_USER)));
			} else {
				clients.remove(channel);
			}
		}
	}
}
