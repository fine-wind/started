package com.example.started.auth.modules.user.v1.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v1.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 头像
     */
    private String headUrl;
    /**
     * 性别   0：男   1：女    2：保密
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 状态  0：停用   1：正常
     *
     * @see com.example.started.auth.modules.sys.enums.UserStatusEnum
     */
    private Integer status;
}
