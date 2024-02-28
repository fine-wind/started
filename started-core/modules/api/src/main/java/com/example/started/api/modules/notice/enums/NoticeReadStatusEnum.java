package com.example.started.api.modules.notice.enums;

/**
 * 通知阅读状态枚举
 */
public enum NoticeReadStatusEnum {
    /**
     * 未读
     */
    UNREAD(0),
    /**
     * 已读
     */
    READ(1);

    private int value;

    NoticeReadStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
