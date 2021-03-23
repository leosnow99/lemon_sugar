package com.sugar.route.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LEOSNOW
 */
@Data
@AllArgsConstructor
public class ChatServerInfo implements Serializable {
    private String id;
    private String address;
    private Integer port;
    private Integer httpPort;
    private Integer count;

    public static ChatServerInfo of(String id, String address, Integer port, Integer count, Integer httpPort) {
        return new ChatServerInfo(id, address, port, httpPort, count);
    }
}
