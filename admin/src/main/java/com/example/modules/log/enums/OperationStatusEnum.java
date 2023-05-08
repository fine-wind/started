package com.example.modules.log.enums;

/**
 * 操作状态枚举
 *
 * @since 1.0.0
 */
public enum OperationStatusEnum {
    /**
     * 失败
     */
    FAIL(0),
    /**
     * 成功
     */
    SUCCESS(1);

    private final int value;

    OperationStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
