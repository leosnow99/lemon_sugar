package com.surgar.util;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class FeignInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		//使用RequestContextHolder工具获取request相关变量
		final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			//取出request
			final HttpServletRequest request = attributes.getRequest();
			//获取头文件信息的key
			final Enumeration<String> headerNames = request.getHeaderNames();
			if (headerNames != null) {
				while (headerNames.hasMoreElements()) {
					//头文件的key
					final String name = headerNames.nextElement();
					//头文件的value
					final String values = request.getHeader(name);
					//将令牌数据添加到头文件中
					requestTemplate.header(name, values);
				}
			}
		}
	}
}
