package com.example.started.log.dto.enums;

/**
 * 登录操作枚举
 *
 * @since 1.0.0
 */
public enum LoginOperationEnum {
    /**
     * 用户登录
     */
    LOGIN(0),
    /**
     * 用户退出
     */
    LOGOUT(1);

    private final int value;

    LoginOperationEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
