package com.example.generator.utils;

import lombok.Getter;

import java.io.Serial;

/**
 * 自定义异常
 */
@Getter
public class MyException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String msg;
    private int code = 500;

    public MyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MyException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MyException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
