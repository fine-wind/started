package com.example.started.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_user")
public class SysRoleUserEntity extends BaseEntity {

    /**
     * 角色名称
     */
    private Long roleId;
    /**
     * 备注
     */
    private Long userId;
}
