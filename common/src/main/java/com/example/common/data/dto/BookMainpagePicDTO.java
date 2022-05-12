package com.example.common.data.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 轮播图片
 *
 *
 * @since 1.0.0 2020-06-22
 */
@Data
@ApiModel(value = "轮播图片")
public class BookMainpagePicDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "点击图片后，跳转的链接")
    private String url;

    @ApiModelProperty(value = "图片路径的id，根据id去file接口获取")
    private Long fileId;

    @ApiModelProperty(value = "排序编号")
    private Integer sort;

    @ApiModelProperty(value = "创建人")
    private Long creator;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

    @ApiModelProperty(value = "更新人")
    private Long updater;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;

    @ApiModelProperty(value = "图片路径,获取图片的")
    private String fileUrl;
}
