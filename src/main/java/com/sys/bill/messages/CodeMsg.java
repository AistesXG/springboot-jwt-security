package com.sys.bill.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Title: CodeMsg
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:45
 */
@Component
public class CodeMsg {


    @Autowired
    private MessageSource messageSource;


    /**
     * 成功Code
     *
     * @return integer
     */
    public Integer successCode() {
        return Integer.valueOf(message("response.success.code"));
    }

    /**
     * 成功Msg
     *
     * @return string
     */
    public String successMsg() {
        return message("response.success.msg");
    }

    /**
     * Token校验错误code
     *
     * @return integer
     */
    public Integer tokenErrorCode() {
        return Integer.valueOf(message("response.tokenError.code"));
    }

    /**
     * Token校验错误msg
     *
     * @return string
     */
    public String tokenErrorMsg() {
        return message("response.tokenError.msg");
    }

    /**
     * 用户被禁用code
     */
    public Integer userDisabledCode() {
        return Integer.valueOf(message("response.userDisable.code"));
    }

    /**
     * 用户被禁用msg
     */
    public String userDisabledMsg() {
        return message("response.userDisable.msg");
    }

    /**
     * 数据库访问异常code
     */
    public Integer dataAccessExceptionCode() {
        return Integer.valueOf(message("response.dataAccessException.code"));
    }

    /**
     * 数据库访问异常msg
     */
    public String dataAccessExceptionMsg() {
        return message("response.dataAccessException.msg");
    }


    /**
     * 用户存在code
     *
     * @return integer
     */
    public Integer userExistCode() {
        return Integer.valueOf(message("response.userExist.code"));
    }

    /**
     * 用户存在msg
     *
     * @return string
     */
    public String userExistMsg() {
        return message("response.userExist.msg");
    }


    /**
     * 登录用户名或密码错误code
     */
    public Integer accountErrorCode() {
        return Integer.valueOf(message("response.accountError.code"));
    }

    /**
     * 登录用户名或密码错误msg
     */
    public String accountErrorMsg() {
        return message("response.accountError.msg");
    }

    /**
     * 失败code
     */
    public Integer failureCode() {
        return Integer.valueOf(message("response.failure.code"));
    }

    /**
     * 失败msg
     */
    public String failureMsg() {
        return message("response.failure.msg");
    }


    /**
     * 非法参数code
     */
    public Integer illegalArgumentCode() {
        return Integer.valueOf(message("response.illegalArgument.code"));
    }

    /**
     * 非法参数msg
     */
    public String illegalArgumentMsg() {
        return message("response.illegalArgument.msg");
    }

    /**
     * 参数校验异常code
     */
    public Integer bindExceptionCode() {
        return Integer.valueOf(message("response.bindException.code"));
    }

    /**
     * 参数校验异常msg
     */
    public String bindExceptionMsg() {
        return message("response.bindException.msg");
    }

    /**
     * 方法参数校验异常code
     */
    public Integer methodArgumentCode() {
        return Integer.valueOf(message("response.methodArgument.code"));
    }

    /**
     * 方法参数校验异常msg
     */
    public String methodArgumentMsg() {
        return message("response.methodArgument.msg");
    }

    /**
     * 身份认证异常code
     *
     * @return integer
     */
    public Integer authenticationExceptionCode() {
        return Integer.valueOf(message("response.authenticationException.code"));
    }

    /**
     * 身份认证异常code
     *
     * @return string
     */
    public String authenticationExceptionMsg() {
        return message("response.authenticationException.msg");
    }

    /**
     * 读取message
     *
     * @param key key
     * @return string
     */
    private String message(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
