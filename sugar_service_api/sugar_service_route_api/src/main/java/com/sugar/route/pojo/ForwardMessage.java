package com.sugar.route.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author bytedance
 */

@Data
@AllArgsConstructor
public class ForwardMessage {
    public String userId;
    public String toUserId;
    public String data;
}
