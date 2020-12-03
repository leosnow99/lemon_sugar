package com.sugar.chat.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 提供手动获取被spring管理的bean对象
 *
 * @author LEOSNOW
 */
public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}
	
	/**
	 * 获取applicationContext
	 * @return 返回Spring容器
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 通过name获取 Bean.
	 * @param name Bean名称
	 * @return Bean对象
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	/**
	 * 通过class获取Bean.
	 * @param clazz class对象
	 * @param <T> 泛型
	 * @return 返回Bean对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	/**
	 * 通过name,以及Clazz返回指定的Bean
	 * @param name Bean名称
	 * @param clazz class对象
	 * @param <T> 泛型
	 * @return Bean对象
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
	
}
