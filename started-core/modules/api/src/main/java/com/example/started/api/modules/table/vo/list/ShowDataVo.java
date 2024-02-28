package com.example.started.api.modules.table.vo.list;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * 列表页面显示处理数据的集合
 */
@Data
public class ShowDataVo {
    /**
     * todo  显示的搜索条件吗?
     */
    private List<?> query;
    /**
     * 页面显示的列
     */
    private List<ShowColumnsVo> columns;
    /**
     * 显示的数据集合
     */
    private List<Object> data = new LinkedList<>();
}
