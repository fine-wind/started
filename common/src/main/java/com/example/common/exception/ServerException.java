package com.example.common.exception;

import com.example.common.constant.Constant;
import com.example.common.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

/**
 * 自定义异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerException extends RuntimeException {
    private int code;
    private String msg;
    private boolean log;

    public ServerException() {
    }

    public ServerException(Constant.UniversalCode universalCode, String... params) {
        this.code = universalCode.getCode();
        this.msg = universalCode.getReasonPhrase();
        if (params != null && params.length > 0) {
            this.msg = Arrays.toString(params);
        }
    }

    public ServerException(int code, String... params) {
        this.code = code;
        this.msg = StringUtil.NULL_STRING;
        if (params != null && params.length > 0) {
            this.msg = Arrays.toString(params);
        }
    }

    public ServerException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = "异常";
    }

    public ServerException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = StringUtil.NULL_STRING;
        if (params != null && params.length > 0) {
            this.msg = Arrays.toString(params);
        }
    }

    public ServerException(String msg) {
        super(msg);
        this.code = UniversalCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
        this.code = UniversalCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

}
