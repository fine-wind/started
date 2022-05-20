package com.example.modules.sys.dto;

import com.example.common.annotation.DictTranslationClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.common.utils.TreeNode;
import com.example.common.validator.group.DefaultGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
public class SysMenuDTO extends TreeNode implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "{sysmenu.name.require}", groups = DefaultGroup.class)
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "类型")
    @Range(min = 0, max = 2, message = "菜单类型不正确", groups = DefaultGroup.class)
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

    @ApiModelProperty(value = "创建时间")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDate;

    @ApiModelProperty(value = "上级菜单名称")
    private String parentName;

}
