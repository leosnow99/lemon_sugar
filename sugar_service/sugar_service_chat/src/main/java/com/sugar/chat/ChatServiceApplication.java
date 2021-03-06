package com.sugar.chat;

import com.sugar.chat.util.IdWorker;
import com.sugar.chat.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * @author LEOSNOW
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.sugar.route.feign"})
public class ChatServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatServiceApplication.class, args);
	}
	
	@Bean
	public SpringUtil springUtil() {
		return new SpringUtil();
	}

	@Bean
	public IdWorker idWorker() {
		return new IdWorker(1,1);
	}
}
