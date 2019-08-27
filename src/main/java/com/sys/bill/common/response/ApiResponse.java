package com.sys.bill.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 调用API接口返回的Response(无返回数据)
 *
 * @author zooqi@senthink.com
 * @date 2018/07/24
 */
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse implements Serializable {

    /**
     * 返回的状态码
     */
    protected int code;

    /**
     * 返回的消息
     */
    protected String msg;
}
