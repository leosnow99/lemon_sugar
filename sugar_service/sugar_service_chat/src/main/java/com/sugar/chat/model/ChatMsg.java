package com.sugar.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author bytedance
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_msg")
public class ChatMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String id;
    public String msgId;
    public String fromId;
    public String toId;
    public String msg;
    public boolean receive;
    public Date createTime;

    public static ChatMsg of(String msgId, String fromId, String toId, String data) {
        ChatMsg msg = new ChatMsg();
        msg.setMsgId(msgId);
        msg.setFromId(fromId);
        msg.setToId(toId);
        msg.setCreateTime(new Date());
        msg.setMsg(data);

        return msg;
    }
}
