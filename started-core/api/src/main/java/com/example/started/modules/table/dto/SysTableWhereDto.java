package com.example.started.modules.table.dto;

import lombok.Data;

@Data
public class SysTableWhereDto {
    private String field;
    private SysTableWhereTypeEnum type;
    private Object value;
}
