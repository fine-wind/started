package com.example.started.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private String realName;
    private String headUrl;
    private String gender;
    private String mobile;
    private String deptId;
    private String multipleLogin;
    private String status;
    private String id_card;
}
