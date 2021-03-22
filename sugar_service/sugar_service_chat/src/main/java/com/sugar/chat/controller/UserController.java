package com.sugar.chat.controller;

import com.sugar.chat.pojo.LoginResult;
import com.sugar.chat.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bytedance
 */
@Slf4j
@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResult login(@RequestParam("username") String userName, @RequestParam("password") String password) {
        LoginResult result = userService.login(userName, password);

        log.info("login username: " + userName + " and password: " + password + " is: " + result.isLogin());
        return result;
    }
}
