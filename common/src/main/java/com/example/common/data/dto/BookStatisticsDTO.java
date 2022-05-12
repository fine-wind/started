package com.example.common.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户行为记录
 *
 *
 * @since 1.0.0 2020-06-29
 */
@Data
@ApiModel(value = "用户行为记录")
public class BookStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 612836348161L;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "书名")
    private String bookName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(name = "出版社")
    private String press;

    @ApiModelProperty(value = "部门")
    private Integer dept;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "阅读类型")
    private Integer readType;

    @ApiModelProperty(value = "会员类型")
    private Integer userType;

    private Integer page;
    private Integer limit;
}
