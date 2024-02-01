package com.example.started.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 部门ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long deptId;

}
