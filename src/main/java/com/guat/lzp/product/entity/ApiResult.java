package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一 API 响应封装
 */
@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    private ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(200, "success", data);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return new ApiResult<>(200, msg, data);
    }

    public static <T> ApiResult<T> error(String msg) {
        return new ApiResult<>(500, msg, null);
    }

    public static <T> ApiResult<T> error(int code, String msg) {
        return new ApiResult<>(code, msg, null);
    }
}
