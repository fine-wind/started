package com.example.started.modules.table.vo.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列表页面显示处理数据的集合
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowEditFieldVo {
    /**
     * 显示标题
     */
    private String title;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;
    /**
     * 默认值
     */
    private String defaultValue;

}
