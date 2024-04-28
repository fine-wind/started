package com.example.started.modules.table.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SysTableDto {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 字段
     */
    private List<String> fields = new ArrayList<>();
    /**
     * 查表条件
     */
    private List<SysTableWhereDto> wheres = new ArrayList<>();

    public SysTableDto putField(String field) {
        this.fields.add(field);
        return this;
    }

}
