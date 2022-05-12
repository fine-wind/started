package com.example.common.data.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 检索关键词
 *
 * @since 1.0.0 2020-06-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "检索关键词")
@EqualsAndHashCode(callSuper = true)
public class SearchKeywordBo extends BaseBo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "图书名称")
    private String keyword;

    @ApiModelProperty(value = "检索时间")
    private Date searchTime;

    @ApiModelProperty(value = "检索次数")
    private Integer num;


}
