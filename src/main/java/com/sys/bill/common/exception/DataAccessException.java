package com.sys.bill.common.exception;

/**
 * 数据库访问异常
 *
 * @author Ethan
 * @date 2017/11/16
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
