package com.sys.bill.common.exception;

/**
 * 请求失败异常
 *
 * @author zooqi@senthink.com
 * @date 2018/7/25
 */
public class BizFailureException extends RuntimeException {

    public BizFailureException() {
    }

    public BizFailureException(String message) {
        super(message);
    }

    public BizFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizFailureException(Throwable cause) {
        super(cause);
    }

    public BizFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
