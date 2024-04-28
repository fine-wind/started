package com.example.started.modules.table.dto;

import com.example.started.modules.table.entity.ShowTableFieldEntity;
import lombok.Getter;


@Getter
public enum SysTableWhereTypeEnum {
    EQ("=", "等于"),
    LIKE("LIKE", "包含"),
    ;
    private final String value;
    private final String title;

    SysTableWhereTypeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }
}