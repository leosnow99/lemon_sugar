package com.sugar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author LEOSNOW
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}
	
	@Bean(name = "ipKeyResolver")
	public KeyResolver userKeyResolver() {
		return exchange -> {
			//获取远程客户端ip
			final String hostAddress = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
			return Mono.just(hostAddress);
		};
	}
}
