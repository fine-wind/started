package com.example.common.v0.data.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 */
@Data
@ApiModel(value = "分页数据")
public class PageData<T> implements Serializable {
    private static final long serialVersionUID = 28725383252672331L;

    @ApiModelProperty(value = "当前页码")
    private long pageNum;

    @ApiModelProperty(value = "当前页记录数")
    private long pageSize;

    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "列表数据")
    private List<T> list;

    /**
     * 分页
     */
    public PageData() {

    }

    /**
     * 分页
     *
     * @param pageNum  当前页码
     * @param pageSize .
     * @param total    .
     * @param list     .
     */
    public PageData(long pageNum, long pageSize, long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
