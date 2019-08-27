package com.sys.bill.common.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Title: BizException
 * @Description: TODO
 * @author: furg@senthink.com
 * @date: 2019/8/27 11:11
 */
@Getter
@Setter
@ToString
public class BizException extends RuntimeException {

    /**
     * error code
     */
    private int code;

    /**
     * error msg
     */
    private String msg;

    public BizException(int code, String msg, Throwable cause) {
        super(code + ":" + cause.getMessage());
        this.code = code;
        this.msg = msg;
    }

    public BizException(int code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }
}
