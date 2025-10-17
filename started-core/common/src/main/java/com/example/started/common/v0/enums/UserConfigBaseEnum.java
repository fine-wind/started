package com.example.started.common.v0.enums;

import lombok.Getter;

/**
 * 用户配置枚举
 *
 * @since 1.0.0
 */
@Getter
public enum UserConfigBaseEnum {
    SUPERMAN("超级管理员"),
    ;
    final String name;

    UserConfigBaseEnum(String name) {
        this.name = name;
    }
}
