package com.sys.bill.common.exception;

/**
 * 参数检验失败异常
 *
 * @author zooqi@senthink.com
 * @date 2018/7/25
 */
public class BizIllegalArgumentException extends RuntimeException {

    public BizIllegalArgumentException() {
    }

    public BizIllegalArgumentException(String message) {
        super(message);
    }

    public BizIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizIllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public BizIllegalArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
