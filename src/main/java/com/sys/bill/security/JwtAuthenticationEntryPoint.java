package com.sys.bill.security;

import com.sys.bill.messages.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Title: JwtAuthenticationEntryPoint
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 14:59
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Autowired
    private CodeMsg codeMsg;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials",  "true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",  "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods",  "*");
        httpServletResponse.setHeader("Access-Control-Allow-Origin",  "*");
        String response = "{\"code\"" + ":" + codeMsg.tokenErrorCode() + ",\"msg\"" + ":" + "\"" + codeMsg.tokenErrorMsg() + "\"}";
        httpServletResponse.getWriter().write(response);
    }
}
