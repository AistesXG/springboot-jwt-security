package com.sys.bill.common.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 调用API接口返回的Response(有返回数据)
 *
 * @param <T>
 * @author zooqi@senthink.com
 * @date 2018/07/24
 */
@Getter
@Setter
public class ApiDataResponse<T> extends ApiResponse {

    /**
     * 返回的数据
     */
    private T data;

    public ApiDataResponse(int code, String msg) {
        super(code, msg);
    }

    public ApiDataResponse(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }
}
