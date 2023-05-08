package com.example.modules.sys.dto;

import com.example.common.v0.annotation.DictTranslationClass;
import com.example.common.v0.utils.TreeNode;
import com.example.common.v0.validator.group.DefaultGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 菜单管理
 *
 * @since 1.0.0
 */
@Data()
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "菜单管理")
@DictTranslationClass()
public class SysMenuDTO extends TreeNode<SysMenuDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "pid")
    private Long pid;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "{sysmenu.name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String path;

    @ApiModelProperty(value = "类型")
    // todo @Range(min = 0, max = 2, message = "菜单类型不正确", groups = DefaultGroup.class)
    private Integer type;

    @ApiModelProperty(value = "是否子页")
    private Integer subpage;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "授权(多个用逗号分隔，如：sys:user:list,sys:user:save)")
    private String permissions;

    @ApiModelProperty(value = "排序")
    @Min(value = 0, message = "{sort.number}", groups = DefaultGroup.class)
    private Integer sort;

    private Integer l;

    private Integer r;

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "上级菜单名称")
    private String parentName;

}
