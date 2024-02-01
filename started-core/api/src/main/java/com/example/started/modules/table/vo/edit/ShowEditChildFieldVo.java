package com.example.started.modules.table.vo.edit;

import lombok.Data;

import java.util.List;

/**
 * 列表页面显示处理数据的集合
 */
@Data
public class ShowEditChildFieldVo {
    /**
     * 显示的标题
     */
    private String title;
    /**
     * 提交时的名称
     */
    private String name;
    private List<ShowEditFieldVo> fields;
}
