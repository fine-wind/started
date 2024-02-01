package com.example.started.modules.sys.enums;

/**
 * 用户状态
 *
 * @since 1.0.0
 */
public enum UserStatusEnum {
    /**
     * 停用
     */
    DISABLE(0),
    /**
     * 正常
     */
    ENABLED(1);

    private final Integer value;

    UserStatusEnum(int value) {
        this.value = value;
    }

    public Integer value() {
        return this.value;
    }
}
