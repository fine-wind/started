package com.example.started.auth.modules.sys.dto;

import com.example.common.v0.validator.group.DefaultGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 行政区域
 */
@Data
@ApiModel(value = "行政区域")
public class SysRegionDTO implements Serializable {

    @ApiModelProperty(value = "区域标识")
    @NotNull(message = "{id.require}", groups = DefaultGroup.class)
    private Long id;

    @ApiModelProperty(value = "上级区域ID")
    @NotNull(message = "{region.pid.require}", groups = DefaultGroup.class)
    private Long pid;

    @ApiModelProperty(value = "区域名称")
    @NotBlank(message = "{region.name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
    private Long sort;

    @ApiModelProperty(value = "上级区域名称")
    private String parentName;

    @ApiModelProperty(value = "是否有子节点")
    private Boolean hasChildren;

    @ApiModelProperty(value = "层级")
    private Integer treeLevel;

    @ApiModelProperty(value = "更新时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updateDate;
}
