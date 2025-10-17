package com.example.started.common.v0.utils;

import com.example.started.common.constant.Constant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 响应数据
 *
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 请求的id
     */
    private Long id;
    /**
     * 编码：200表示成功，其他值表示失败
     */
    private Integer code = 200;
    /**
     * 消息内容
     */
    private String msg = "success";
    /**
     * 请求是否成功
     */
    private boolean success = false;
    /**
     * 响应数据
     */
    private T data;

    public boolean isSuccess() {
        return success = code == 200;
    }


    public Result<T> setCode(int code, String msg) {
        this.code = code;
        this.setMsg(msg);
        return this;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return new Result<>();
    }

    public static <T> Result<T> ok(T t) {
        return new Result<>(t);
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<T>().setMsg(msg);
    }

    public static Result<?> error(String msg) {
        return Result.error(Constant.UniversalCode.SERVER_ERROR, msg);
    }

    @Deprecated
    public static <T> Result<T> error(int code, String msg) {
        Result<T> r = new Result<>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public Result<T> error() {
        this.code = Constant.UniversalCode.SERVER_ERROR.getCode();
        this.msg = "异常";
        return this;
    }


    @Deprecated
    public Result<T> error(int code) {
        this.code = code;
        this.msg = "异常";
        return this;
    }

    public static <T> Result<T> error(Constant.UniversalCode universalCode, String... params) {
        Result<T> error = Result.error(universalCode.getCode(), universalCode.getReasonPhrase());
        if (params != null && params.length > 0) {
            error.msg = Arrays.toString(params);
        }
        return error;
    }
}
