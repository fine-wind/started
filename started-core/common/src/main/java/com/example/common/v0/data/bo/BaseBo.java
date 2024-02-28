package com.example.common.v0.data.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共Bo
 */
@Data
@Deprecated
@Accessors(chain = true)
@ApiModel(value = "公共Bo")
public class BaseBo implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private Long updater;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 分页
     */
    PageBo page = new PageBo();

}
