package com.example.started.api.modules.table.vo.edit;

import lombok.Data;

/**
 * 列表页面显示处理数据的集合
 */
@Data
public class ShowEditFieldVo {

    private String title;
    private String name;
    private String type;
    private String defaultValue;

    public ShowEditFieldVo() {
    }

}
