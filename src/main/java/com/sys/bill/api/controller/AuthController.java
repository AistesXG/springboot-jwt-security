package com.sys.bill.api.controller;

import com.sys.bill.auth.AuthService;
import com.sys.bill.auth.JWTResponse;
import com.sys.bill.auth.LoginRequest;
import com.sys.bill.auth.RegisterUserRequest;
import com.sys.bill.common.response.ApiDataResponse;
import com.sys.bill.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Title: AuthController
 * @Description: 处理认证登录的controller
 * @author: furg@senthink.com
 * @date: 2019/8/27 16:25
 */
@Api(tags = "Authentication", description = "鉴权管理")
@RestController
public class AuthController {


    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private AuthService authService;


    @PostMapping("${uri.auth.login}")
    @ApiOperation(value = "登录", notes = "用户登录")
    public ApiDataResponse<JWTResponse> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request.getUsername(), request.getPassword());
    }

    @PostMapping("${uri.auth.register}")
    @ApiOperation(value = "注册", notes = "用户注册（管理员添加用户）")
    public ApiDataResponse<User> register(@RequestBody @Valid RegisterUserRequest request) {
        return authService.register(request);
    }

    @GetMapping("${uri.auth.refresh}")
    @ApiOperation(value = "刷新JWT", notes = "获取新的JWT")
    public ApiDataResponse<JWTResponse> refresh(HttpServletRequest request) {
        String oldToken = request.getHeader(tokenHeader);
        return authService.refresh(oldToken);
    }
}
