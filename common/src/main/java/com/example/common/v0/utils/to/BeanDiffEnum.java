package com.example.common.v0.utils.to;

import lombok.Data;
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
