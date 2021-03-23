package com.sugar.client.util;

import com.sugar.chat.pojo.ChatMessage;
import com.sugar.chat.pojo.ChatMessageFactory;
import com.sugar.chat.pojo.LoginResult;
import com.sugar.chat.pojo.TransferMessage;
import com.sugar.client.netty.Client;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Scanner;

/**
 * @author bytedance
 */
public class HandleCommand {
    public static void handle(String str) {
        Command command = convert(str);

        switch (command) {
            case HELP:
                help();
                break;
            case SEND:
                sendMsg();
                break;
            case QUIT:
                quit();
                break;
            case LOGIN:
                login();
                break;
            default:
                System.out.println("oops, 不支持该指令，请输入`#help`获取帮助");
        }

    }

    public static Command convert(String command) {
        String commandPrefix = "#";
        if (StringUtils.isEmpty(command) || !command.trim().startsWith(commandPrefix)) {
            return Command.UNKNOWN;
        }
        command = command.substring(1);
        switch (command) {
            case "quit":
                return Command.QUIT;
            case "help":
                return Command.HELP;
            case "send":
                return Command.SEND;
            case "login":
                return Command.LOGIN;
            default:
                return Command.UNKNOWN;
        }
    }

    public static void help() {
        System.out.println("------------------------------");
        System.out.println("|           操作指南           |");
        System.out.println("|       登   录：#login        |");
        System.out.println("|       发送消息：#send         |");
        System.out.println("|       退   出：#quit         |");
        System.out.println("|       帮   助：#help         |");
        System.out.println("------------------------------");
    }

    public static void main(String[] args) {
        String command = "#quit";
        System.out.println(command.substring(1));
    }

    public static void quit() {
        if (!Client.channel.isActive()) {
            System.out.println("服务器已经关闭！");
        } else {
            Client.channel.close();
            System.out.println("服务器关闭成功！");
        }
        System.out.println("退出系统！");
        System.exit(0);
    }

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        BodyInserters.FormInserter<String> data = BodyInserters.fromFormData("username", username).with("password", password);
        WebClient webClient = WebClient.builder().baseUrl("http://127.0.0.1:6100").build();
        LoginResult result = webClient.post()
                .uri("/user/login")
                .body(data)
                .retrieve()
                .bodyToMono(LoginResult.class).block();

        // 链接
        if (result != null && result.isLogin()) {
            System.out.println("登录成功");
            Client.setInfo(result.getIp(), result.getPort(), username);
            Client.connectChatServer();
            return;
        }
        System.out.println("登录失败");
    }

    public static void sendMsg() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入接受者id：");
        String toUserId = scanner.nextLine();
        System.out.println("请输入发送的消息");
        String msg = scanner.nextLine();

        ChatMessage.Message message = ChatMessageFactory.ofSendChatMessage(ChatMessage.MsgActionEnum.CHAT, Client.id, toUserId, msg);
        if (!Client.channel.isActive()) {
            System.out.println("服务已经断开");
            Client.connectChatServer();
        }
        ChannelFuture future = Client.channel.writeAndFlush(TransferMessage.of(message));
        future.addListener((ChannelFutureListener) channelFuture ->
                System.out.println("客户端手动发消息成功=" + msg));
    }
}
