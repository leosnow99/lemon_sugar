package com.sugar.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author LEOSNOW
 */
@RestController
@Slf4j
@RequestMapping("/webflux")
public class WebFluxDemo {
	
	private String createStr() {
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "some thing";
	}
	
	@GetMapping
	public String hello() {
		log.info("get start");
		String result = createStr();
		log.info("get1 end.");
		return result;
	}
	
	@GetMapping("/flux")
	public Mono<String> helloFlux() {
		log.info("get2 start");
		final Mono<String> result = Mono.just("this is async web return")
				.doOnNext(System.out::println)
				//这里同样阻塞xx秒
				.delayElement(Duration.ofSeconds(5)).doOnNext(s -> log.info("1"));
		log.info("get2 end.");
		return result;
	}
	
	@GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	private Flux<String> flux() {
		return Flux.fromStream(IntStream.range(1, 5).mapToObj(i -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException ignored) {
			}
			return "flux data--" + i;
		}));
	}
	
	@Autowired
	RSocketRequester.Builder builder;
	
	@GetMapping("/demo")
	public Mono<String> demo(){
		final Mono<RSocketRequester> resocket = builder.connectTcp("localhost", 7070);
		resocket.doOnSuccess(scoket -> {
			scoket.route("echo").data("hello").retrieveMono(String.class);
		});
		return builder.connectTcp("localhost", 7070).block().route("echo").data("hello").retrieveMono(String.class);
	}
}
