package com.sugar.route.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bytedance
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForwardMessage {
    public String userId;
    public String toUserId;
    public String data;
    public String msgId;
}
