package com.sugar.route.controller;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.ForwardMessage;
import com.sugar.route.service.RouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author LEOSNOW
 */
@Slf4j
@RestController
@RequestMapping("/route")
public class RouteController {
	
	@Autowired
	private RouteService routeService;

	@PostMapping("/register")
	public void register(@RequestBody ChatServerInfo chatServerInfo) {
		if (chatServerInfo == null) {
			return;
		}
		System.out.println("收到消息: " + chatServerInfo.getAddress() + ":" + chatServerInfo.getPort());
		routeService.register(chatServerInfo);
	}
	
	@GetMapping("/chatService")
	public ChatServerInfo getChatService() {
		return routeService.getChatServer();
	}

	@PostMapping("/forward/message")
	public void sendMessage(@RequestBody ForwardMessage message) {
		log.info("received message from userID: " + message.getUserId());

	}
}
