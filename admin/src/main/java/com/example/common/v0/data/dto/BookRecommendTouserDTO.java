package com.example.common.v0.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 推荐对象
 *
 *
 * @since 1.0.0 2020-06-23
 */
@Data
@ApiModel(value = "推荐对象")
public class BookRecommendTouserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "book_recommend表id")
	private Long recmdId;

	@ApiModelProperty(value = "推荐方式：1：按部门推荐，2：按会员")
	private Long type;

	@ApiModelProperty(value = "部门id")
	private Long orgid;

	@ApiModelProperty(value = "部门名称")
	private String orgname;

	@ApiModelProperty(value = "用户id")
	private Long userid;

	@ApiModelProperty(value = "用户名称")
	private String username;

	@ApiModelProperty(value = "开始日期")
	private Date startDate;

	@ApiModelProperty(value = "结束日期")
	private Date endDate;


}
