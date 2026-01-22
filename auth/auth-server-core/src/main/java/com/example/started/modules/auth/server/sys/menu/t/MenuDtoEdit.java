package com.example.started.modules.auth.server.sys.menu.t;

import com.example.started.common.v0.validator.group.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 系统菜单
 */
@Data
public class MenuDtoEdit {

    @NotBlank(groups = UpdateGroup.class, message = "主键不能为空")
    private Long id;
    /**
     * pid
     * 0：根节点
     * >0：父菜单ID
     */
    @NotNull(groups = UpdateGroup.class, message = "父节点不能为空")
    @Min(value = 0, groups = UpdateGroup.class, message = "父节点ID不能小于0")
    private Long pid;

    @NotNull(groups = UpdateGroup.class, message = "类型不能为空")
    @Min(value = 1, groups = UpdateGroup.class, message = "类型值错误")
    @Max(value = 3, groups = UpdateGroup.class, message = "类型值错误")
    private Integer type;
    /**
     * icon
     */
    private String icon;

    /**
     * 标题
     */
    @NotBlank(groups = UpdateGroup.class, message = "标题不能为空")
    @Size(min = 2, max = 20, groups = UpdateGroup.class, message = "标题长度2-20字符")
    private String title;

    /**
     * path
     */
    @NotBlank(groups = UpdateGroup.class, message = "路由路径不能为空")
    @Pattern(regexp = "^/.*", groups = UpdateGroup.class, message = "路由路径必须以/开头")
    private String path;

    /**
     * 用户状态
     * 0：不显示
     * 1：正常使用
     */
    @NotNull(groups = UpdateGroup.class, message = "状态不能为空")
    @Min(value = 0, groups = UpdateGroup.class, message = "状态值错误")
    @Max(value = 1, groups = UpdateGroup.class, message = "状态值错误")
    private Integer state;

    @Min(value = -9999, groups = UpdateGroup.class, message = "排序太小了")
    @Max(value = 9999, groups = UpdateGroup.class, message = "排序太大了")
    private Integer sort;
}