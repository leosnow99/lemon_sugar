package com.sugar.route.feign;

import com.sugar.route.pojo.ChatServerInfo;
import com.sugar.route.pojo.ForwardMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author LEOSNOW
 */
@FeignClient(value = "IM-ROUTE")
public interface RouteFeign {
	
	/**
	 * 服务器上线, 注册
	 *
	 * @param chatServerInfo 服务器信息
	 */
	@PostMapping("/route/register")
	void register(@RequestBody ChatServerInfo chatServerInfo);

	/**
	 * 跨服务实例转发消息
	 * @param message 转发消息体
	 */
	@PostMapping("/route/forward/message")
	void sendMessage(@RequestBody ForwardMessage message);
}
