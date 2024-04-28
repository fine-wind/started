package com.example.started.modules.table.vo.list;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.*;

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
    private List<ShowColumnsVo> columns = new LinkedList<>();
    /**
     * 页面显示的操作按钮
     */
    private List<ShowOpVo> operates = new LinkedList<>();
    /**
     * 显示的数据集合
     */
    private List<Object> data = new LinkedList<>();
    /**
     * 分页
     */
    private PaginationVo pagination = new PaginationVo();

    public void putColumns(ShowColumnsVo columns) {
        this.columns.add(columns);
    }

    public void putData(List<?> obj) {
        this.data.addAll(obj);
    }

    public void putOperate(ShowOpVo obj) {
        this.operates.add(obj);
    }

    public void putRecords(IPage<?> page) {
        this.putData(page.getRecords());

        pagination.setSize(page.getSize());
        pagination.setCurrent(page.getCurrent());
        pagination.setTotal(page.getTotal());
    }
}
