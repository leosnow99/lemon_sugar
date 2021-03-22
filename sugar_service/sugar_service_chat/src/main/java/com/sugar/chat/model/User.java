package com.sugar.chat.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author bytedance
 */
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    public String id;
    public String userName;
    public String password;
    public Date createTime;
}
