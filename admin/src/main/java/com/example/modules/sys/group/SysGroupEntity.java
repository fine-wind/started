package com.example.modules.sys.group;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;

@TableName("sys_group")
public class SysGroupEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private Long groupId;
    /**
     * 菜单ID
     */
    private Long Id;
}