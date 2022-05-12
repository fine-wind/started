package com.example.common.data.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Token
 */
@Data
public class LoginUserBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @JSONField(serialize = false)
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
     * 部门ID
     */
    private Long deptId;
    /**
     * 超级管理员   0：否   1：是
     */
    private Integer superAdmin;

    /**
     * 状态  0：停用   1：正常
     */
    private Integer status;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 身份证号
     */
    private String idCard;


}
