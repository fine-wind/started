package com.example.started.api.modules.table.vo.edit;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 列表页面显示处理数据的集合
 */
@Data
public class ShowEditVo {
    /**
     * 格式集合
     */
    private List<ShowEditFieldVo> fields;
    /**
     * 子表格式集合
     */
    private List<ShowEditChildFieldVo> childFields;

    public ShowEditVo() {
        this.fields = new LinkedList<>();
        this.childFields = new LinkedList<>();
    }
}
