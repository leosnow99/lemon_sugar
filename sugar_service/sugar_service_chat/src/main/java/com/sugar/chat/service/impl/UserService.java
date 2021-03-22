package com.sugar.chat.service.impl;

import com.sugar.chat.dao.UserRepository;
import com.sugar.chat.model.User;
import com.sugar.chat.pojo.LoginResult;
import com.sugar.route.feign.RouteFeign;
import com.sugar.route.pojo.ChatServerInfo;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bytedance
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteFeign routeFeign;

    public LoginResult login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return LoginResult.loginFailed();
        }
        User user = userRepository.findUserByUseAndUserNameAndPassword(userName, password);
        if (user == null) {
            return LoginResult.loginFailed();
        }
        ChatServerInfo chatService = routeFeign.getChatService();
        LoginResult result = new LoginResult();
        result.setLogin(true);
        result.setIp(chatService.getAddress());
        result.setPort(chatService.getPort());

        return result;
    }
}
