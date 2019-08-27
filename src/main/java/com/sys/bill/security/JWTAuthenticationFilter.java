package com.sys.bill.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys.bill.common.exception.DataAccessException;
import com.sys.bill.common.response.ApiErrorResponse;
import com.sys.bill.messages.CodeMsg;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * JWT校验Filter
 *
 * @author zooqi@senthink.com
 * @date 2018/07/26
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String jwtHeader;
    @Value("${jwt.prefix}")
    private String jwtPrefix;
    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserDetailsService userDetailsService;


    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String jwt = fetchJWT(request);
        if (StringUtils.isBlank(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username;
        try {
            username = jwtTokenUtils.getUsernameFromToken(jwt);
        } catch (Exception e) {
            LOGGER.warn("Fail to parse username from JWT", e);
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;
            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (Exception e) {
                LOGGER.warn("Fail to load UserDetails", e);
                if (e instanceof DataAccessException) {
                    processDataAccessException(response, e);
                } else {
                    filterChain.doFilter(request, response);
                }
                return;
            }

            if (!userDetails.isEnabled()) {
                processDisabled(response, username);
                return;
            }

            if (jwtTokenUtils.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * 从{@link HttpServletRequest}中解析出JWT
     *
     * @param request
     * @return
     */
    private String fetchJWT(HttpServletRequest request) {
        String jwt = null;

        String parameterJwt = request.getParameter(jwtHeader);
        try {
            if (StringUtils.isNotBlank(parameterJwt)) {
                jwt = parameterJwt;
            } else {
                String headerJwt = request.getHeader(jwtHeader);
                if (StringUtils.isNotBlank(headerJwt)) {
                    jwt = headerJwt.substring(jwtPrefix.length());
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Bad JWT: {}", jwt);
            return null;
        }
        return jwt;
    }

    /**
     * 处理用户禁用事件
     *
     * @param response
     * @param username
     */
    private void processDisabled(HttpServletResponse response, String username) throws IOException {
        ApiErrorResponse<String> result =
                new ApiErrorResponse<>(codeMsg.userDisabledCode(), codeMsg.userDisabledMsg(), username);
        internalServerError(response, result);
    }

    /**
     * 处理{@link DataAccessException}
     *
     * @param response
     * @param e
     */
    private void processDataAccessException(HttpServletResponse response, Exception e) throws IOException {
        ApiErrorResponse<String> result =
                new ApiErrorResponse<>(codeMsg.dataAccessExceptionCode(), codeMsg.dataAccessExceptionMsg(), e.getMessage());
        internalServerError(response, result);
    }

    /**
     * 返回{@link HttpStatus#INTERNAL_SERVER_ERROR}
     *
     * @param response
     * @param
     * @throws IOException
     */
    private void internalServerError(HttpServletResponse response, ApiErrorResponse<String> error) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        PrintWriter printWriter = response.getWriter();
        printWriter.print(objectMapper.writeValueAsString(error));
        printWriter.flush();
    }
}
