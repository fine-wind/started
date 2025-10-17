package com.example.started.common.v0.utils.to;

import lombok.Getter;


public enum BeanDiffEnum {
    ADD("ADD"),
    DELETE("DELETE"),
    UPDATE("UPDATE"),
    ;
    @Getter
    private final String code;

    BeanDiffEnum(String code) {
        this.code = code;
    }
}
