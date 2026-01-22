package com.example.started.common.constant;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页入参类
 */
@Data
@NoArgsConstructor
public class PageBo {
    //每页记录数
    private int pageSize;
    //当前页码
    private int currPage;

    public String toLimit() {
        return "limit " + (Math.max(this.currPage, 1) - 1) * this.pageSize + "," + this.pageSize;
    }

}
