package com.example.started.auth.modules.sys.groupResources;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;

@TableName("sys_group_resources")
public class SysGroupResourcesEntity extends BaseEntity {
    /**
     * 角色ID
     */
    private Long groupId;
    /**
     * 菜单ID
     */
    private Long resourcesId;
}
