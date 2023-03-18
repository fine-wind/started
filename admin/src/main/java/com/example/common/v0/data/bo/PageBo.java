package com.example.common.v0.data.bo;

import com.example.common.v0.constant.Constant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分页Bo
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "分页Bo")
public class PageBo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@link Constant.PAGE#ORDER_FIELD}
     */
    @ApiModelProperty(value = "排序字段")
    private String orderField;
    /**
     * {@link Constant.PAGE#ORDER}
     */
    @ApiModelProperty(value = "排序方式")
    private String order;

    /**
     * 当前页码
     */
    private Integer page = 1;
    /**
     * 每页显示记录数
     */
    private Integer limit = 10;

    public PageBo() {
    }

    public PageBo(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }
}
