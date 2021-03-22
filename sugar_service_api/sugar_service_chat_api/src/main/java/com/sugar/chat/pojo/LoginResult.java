package com.sugar.chat.pojo;

import lombok.Data;

/**
 * @author bytedance
 */
@Data
public class LoginResult {
    public boolean isLogin;
    public String ip;
    public Integer port;

    public static LoginResult loginFailed() {
        LoginResult loginResult = new LoginResult();
        loginResult.setLogin(false);
        return loginResult;
    }
}
