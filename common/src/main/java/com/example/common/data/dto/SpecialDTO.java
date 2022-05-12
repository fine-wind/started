package com.example.common.data.dto;

import com.example.common.data.entity.BookInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 图书专题表
 *
 *
 * @since 1.0.0 2020-07-10
 */
@Data
@ApiModel(value = "图书专题表")
public class SpecialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "专题名称")
	private String name;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;

	@ApiModelProperty(value = "图书列表")
	List<BookInfoEntity> bookList;


}
