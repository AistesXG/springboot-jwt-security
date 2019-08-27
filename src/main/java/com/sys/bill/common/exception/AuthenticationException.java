package com.sys.bill.common.exception;

/**
 * @Title: AuthenticationException
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:09
 */
public class AuthenticationException extends RuntimeException{

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

    public AuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
