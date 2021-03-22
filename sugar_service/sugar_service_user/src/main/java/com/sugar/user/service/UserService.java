package com.sugar.user.service;

import com.sugar.user.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bytedance
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
}
