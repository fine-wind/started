package com.example.started.auth.modules.sys.dept.dto;

import com.example.common.v0.utils.TreeNode;
import com.example.common.v0.validator.group.AddGroup;
import com.example.common.v0.validator.group.DefaultGroup;
import com.example.common.v0.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.io.Serializable;
import java.util.List;

/**
 * 部门管理
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门管理")
public class SysDeptDTO extends TreeNode<SysDeptDTO> implements Serializable {

    @ApiModelProperty(value = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "上级ID")
    @NotNull(message = "{sysdept.pid.require}", groups = DefaultGroup.class)
    private Long pId;

    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "{sysdept.name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
    private Integer sort;


    @ApiModelProperty(value = "资源")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> resources;

}
