package com.example.common.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 检索关键词
 *
 *
 * @since 1.0.0 2020-06-25
 */
@Data
@ApiModel(value = "检索关键词")
public class SearchKeywordDTO implements Serializable {
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
