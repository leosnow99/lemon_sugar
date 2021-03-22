package com.sugar.chat.controller;

import com.sugar.chat.service.ChatService;
import com.sugar.chat.util.ChannelSocketHolder;
import com.sugar.route.pojo.ForwardMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author bytedance
 */
@RestController
@RequestMapping("/")
public class DemoController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ChatService chatService;

    @GetMapping
    public void consulDemo() {


        System.out.println("----------------");
        discoveryClient.getServices().forEach(System.out::println);
        final List<ServiceInstance> instances = discoveryClient.getInstances("IMSENTINEL");
        final ServiceInstance serviceInstance = instances.get(0);
        serviceInstance.getMetadata().forEach((s, s2) -> System.out.println("key: " + s + " value: " + s2));
//		final ConsulClient consulClient = new ConsulClient("218.198.180.38");
//		HealthServicesRequest.newBuilder()
//				.set
//		consulClient.getHealthServices("IMSENTINEL", );
    }

    /**
     * 路由健康检查
     *
     * @return 回复目前服务器注册的服务数量
     */
    @GetMapping("/route/health")
    public Integer checkHealth() {
        return ChannelSocketHolder.getUserCount();
    }

    /**
     * 接收route 传送的转发消息
     * @param forwardMessage 转发消息体
     */
    @PostMapping("/message")
    public void forwardMessage(@RequestBody ForwardMessage forwardMessage) {
        if (forwardMessage != null) {
            chatService.forwardMsg(forwardMessage);
        }
    }
}
