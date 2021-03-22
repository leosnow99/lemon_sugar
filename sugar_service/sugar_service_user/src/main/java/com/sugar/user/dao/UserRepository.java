package com.sugar.user.dao;

import com.sugar.user.mode.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author bytedance
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<String> {
}
