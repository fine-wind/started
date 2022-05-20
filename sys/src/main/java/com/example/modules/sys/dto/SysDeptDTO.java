package com.example.modules.sys.dto;

import com.example.common.annotation.DictTranslationClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.common.utils.TreeNode;
import com.example.common.validator.group.AddGroup;
import com.example.common.validator.group.DefaultGroup;
import com.example.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 部门管理
 *
 * @since 1.0.0
 */
@Data
@ApiModel(value = "部门管理")
@EqualsAndHashCode(callSuper = true)
@DictTranslationClass
public class SysDeptDTO extends TreeNode implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @Null(message = "{id.null}", groups = AddGroup.class)
    @NotNull(message = "{id.require}", groups = UpdateGroup.class)
    private Long id;

    @ApiModelProperty(value = "上级ID")
    @NotNull(message = "{sysdept.pid.require}", groups = DefaultGroup.class)
    private Long pid;

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
