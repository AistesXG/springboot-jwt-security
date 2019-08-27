package com.sys.bill.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于在登录成功后返回JWT的Response类
 *
 * @author zooqi@senthink.com
 * @date 2018/07/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTResponse {

    /**
     * JWT token
     */
    private String jwt;

    /**
     * 过期时间
     */
    private Long expireAt;

    /**
     * 用户角色
     */
    private String role;
}
