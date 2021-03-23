package com.sugar.client.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.util.StringUtils;

/**
 * @author bytedance
 */
public class Client {
    public static SocketChannel channel;
    public static String ip;
    public static Integer port;
    public static String id;

    public static void setInfo(String setIp, Integer setPort, String setId) {
        ip = setIp;
        port = setPort;
        id = setId;
    }

    public static void connectChatServer() {
        try {
            final NioEventLoopGroup workGroup = new NioEventLoopGroup();
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class).handler(new ClientInitializer());

            if (StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
                return;
            }
            System.out.println("ip: " + ip + " port: " + port);
            final ChannelFuture future = bootstrap.connect(ip, port).sync();
            channel = (SocketChannel) future.channel();
            System.out.println("链接服务器成功");
        } catch (InterruptedException e) {
            System.out.println("链接服务器异常： " + e.getMessage());
        }
    }
}
