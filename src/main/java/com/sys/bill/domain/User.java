package com.sys.bill.domain;

import com.sys.bill.common.entity.BornableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Title: User
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:19
 */
@Entity
@Table(name = "bill_users", indexes = {
        @Index(name = "idx_users_uid", columnList = "uid", unique = true),
        @Index(name = "idx_users_mobile", columnList = "mobile", unique = true),
        @Index(name = "idx_users_email", columnList = "email", unique = true),
        @Index(name = "idx_users_account", columnList = "account", unique = true)
})
@Setter
@Getter
public class User extends BornableEntity<Long> {

    /**
     * user unique id
     */
    @Column(name = "uid", nullable = false)
    private String uid;

    /**
     * user name
     */
    @Column(name = "user_name", nullable = false)
    private String name;

    /**
     * user mobile
     */
    @Column(name = "mobile", nullable = false)
    private String mobile;

    /**
     * user email
     */
    @Column(name = "email")
    private String email;

    /**
     * user account
     */
    @Column(name = "account", nullable = false)
    private String account;

    /**
     * password
     */
    @Column(name = "pwd", nullable = false)
    private String password;

    /**
     * user role
     */
    @Column(name = "user_role", nullable = false)
    private String role;

    /**
     * remarks
     */
    @Column(name = "remarks")
    private String remarks;


    /**
     * 最近一次重置密码的时间
     */
    @Column(name = "last_reset_date")
    private Date lastPwdRestDate;


    @Column(name = "enable")
    private boolean enable;

}
