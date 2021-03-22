package com.sugar.chat.dao;


import com.sugar.chat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author bytedance
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<String> {
    /**
     * 根据用户名和密码查找用户是否存在
     *
     * @param userName 用户名
     * @param password 密码
     * @return 用户实例
     */
    User findUserByUseAndUserNameAndPassword(String userName, String password);
}
