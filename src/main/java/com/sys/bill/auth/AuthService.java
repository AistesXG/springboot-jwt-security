package com.sys.bill.auth;

import com.sys.bill.common.exception.BizException;
import com.sys.bill.common.exception.DataAccessException;
import com.sys.bill.common.response.ApiDataResponse;
import com.sys.bill.common.util.RandomUtils;
import com.sys.bill.domain.User;
import com.sys.bill.messages.CodeMsg;
import com.sys.bill.security.CurrentUser;
import com.sys.bill.security.JwtTokenUtils;
import com.sys.bill.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Title: AuthService
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 15:47
 */
@Service
public class AuthService {

    @Autowired
    private CodeMsg codeMsg;
    @Value("${jwt.prefix}")
    private String tokenPrefix;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    /**
     * 注册用户
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {DataAccessException.class, BizException.class})
    public ApiDataResponse<User> register(RegisterUserRequest request) {
        boolean isExist = userService.isUserExist(request.getAccount(), request.getMobile(), request.getEmail());

        if (isExist) {
            throw new BizException(codeMsg.userExistCode(), codeMsg.userExistMsg());
        }
        final String password = request.getPassword();
        request.setPassword(passwordEncoder.encode(password));
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setLastPwdRestDate(new Date());
        user.setEnable(true);
        user.setUid(RandomUtils.randomString(30));
        user = userService.saveUser(user);
        return new ApiDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), user);
    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public ApiDataResponse<JWTResponse> login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (DisabledException e) {
            throw new BizException(codeMsg.userDisabledCode(), codeMsg.userDisabledMsg());
        } catch (Exception e) {
            throw new BizException(codeMsg.accountErrorCode(), codeMsg.accountErrorMsg());
        }
        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
        JWTResponse response;
        try {
            String jwt = jwtTokenUtils.generateToken(currentUser);
            long expireAt = jwtTokenUtils.getIssuedAtDateFromToken(jwt).getTime() / 1000;
            response = new JWTResponse(jwt, expireAt, currentUser.getUser().getRole());
        } catch (Exception e) {
            throw new BizException(codeMsg.failureCode(), codeMsg.failureMsg());
        }
        return new ApiDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), response);

    }

    /**
     * 刷新JWT
     *
     * @param oldToken
     * @return
     */
    public ApiDataResponse<JWTResponse> refresh(String oldToken) {
        if (StringUtils.isBlank(oldToken)) {
            return new ApiDataResponse<>(codeMsg.tokenErrorCode(), codeMsg.tokenErrorMsg());
        }
        String token = oldToken.substring(tokenPrefix.length());
        String username = jwtTokenUtils.getUsernameFromToken(token);
        CurrentUser currentUser = (CurrentUser) userDetailsService.loadUserByUsername(username);
        boolean canRefresh = jwtTokenUtils.canTokenBeRefreshed(token, currentUser.getUser().getLastPwdRestDate());
        if (canRefresh) {
            String newToken = jwtTokenUtils.refreshToken(token);
            long expireAt = jwtTokenUtils.getExpirationDateFromToken(newToken).getTime();
            JWTResponse response;
            try {
                response = new JWTResponse(newToken, expireAt, currentUser.getUser().getRole());
            } catch (Exception e) {
                throw new BizException(codeMsg.failureCode(), codeMsg.failureMsg());
            }
            return new ApiDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), response);
        }
        return new ApiDataResponse<>(codeMsg.failureCode(), codeMsg.failureMsg());
    }

}
