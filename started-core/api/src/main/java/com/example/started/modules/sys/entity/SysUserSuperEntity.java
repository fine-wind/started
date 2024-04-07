package com.example.started.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户-超级管理员
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_super")
public class SysUserSuperEntity extends BaseEntity {

    private String userId;
    private String status;
    private String companyId;
}
