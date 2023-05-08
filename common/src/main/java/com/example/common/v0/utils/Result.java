package com.example.common.v0.utils;

import com.example.common.v0.constant.Constant;
import lombok.Data;
import com.example.common.v0.exception.UniversalCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 响应数据
 *
 * @since 1.0.0
 */
@ApiModel(value = "响应")
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
    @ApiModelProperty(value = "编码：200表示成功，其他值表示失败")
    private Integer code = 200;
    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容")
    private String msg = "success";
    /**
     * 请求是否成功
     */
    private boolean success = false;
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * todo 这里要改成其他形式返回 token 避免被刷
     */
    private String token;

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

    public static Result<?> error(String msg) {
        Result<Object> objectResult = new Result<>();
        objectResult.code = UniversalCode.INTERNAL_SERVER_ERROR;
        objectResult.msg = msg;
        return objectResult;
    }

    public static Result<?> error(int code, String msg) {
        Result<Object> r = new Result<>();
        r.code = code;
        r.msg = msg;
        return r;
    }

    public Result<T> error() {
        this.code = UniversalCode.INTERNAL_SERVER_ERROR;
        this.msg = "异常";
        return this;
    }


    public Result<T> error(int code) {
        this.code = code;
        this.msg = "异常";
        return this;
    }

    public static Result<?> error(Constant.UniversalCode universalCode, String... params) {
        Result<?> error = Result.error(universalCode.getCode(), universalCode.getReasonPhrase());
        if (params != null && params.length > 0) {
            error.msg = Arrays.toString(params);
        }
        return error;
    }
}
