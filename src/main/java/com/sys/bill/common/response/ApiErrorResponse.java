package com.sys.bill.common.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用API接口返回的Response(有错误信息)
 *
 * @param <T>
 * @author zooqi@senthink.com
 * @date 2018/07/24
 */
@Getter
@Setter
public class ApiErrorResponse<T> extends ApiResponse {

    /**
     * 错误信息
     */
    private T error;

    public ApiErrorResponse(int code, String msg, T error) {
        super(code, msg);
        this.error = error;
    }
}
