package com.example.modules.master.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.modules.sys.enums.UserStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_super")
@Deprecated
public class SysSuperUserEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String userId;

    /**
     * 状态  0：停用   1：正常
     *
     * @see UserStatusEnum
     */
    private Integer status;
}
