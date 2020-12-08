package com.sugar.route.algorithm;


import com.sugar.route.pojo.ChatServerInfo;
import org.apache.catalina.util.ServerInfo;

/**
 * @author LEOSNOW
 */
public interface AllocationAlgorithm {
	/**
	 * 获取一个IM服务器实例
	 * @return 服务器实例信息
	 */
	ChatServerInfo getServer();
	
}
