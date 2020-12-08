package com.sugar.route.controller;

import com.sugar.route.netty.ChatServerHandler;
import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LEOSNOW
 */
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
		ChatServerHandler.chatServerRegister(chatServerInfo);
		System.out.println("收到消息: " + chatServerInfo.getAddress() + ":" + chatServerInfo.getPort());
		routeService.register(chatServerInfo);
	}
}
