package com.example.modules.sys.userConf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户个性化配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_conf")
public class SysUserConfEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 配置项
     */
    private String userId;
    /**
     * 配置项
     */
    private String item;
    /**
     * 配置项内容
     */
    private String content;

}
