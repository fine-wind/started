package com.example.common.v0.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 专题图书列表
 *
 *
 * @since 1.0.0 2020-07-10
 */
@Data
@ApiModel(value = "专题图书列表")
public class SpecialListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键id")
	private Long id;

	@ApiModelProperty(value = "专题id")
	private Long spid;

	@ApiModelProperty(value = "图书id")
	private Long bookid;


}
