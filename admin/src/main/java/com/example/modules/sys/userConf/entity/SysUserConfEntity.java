package com.example.modules.sys.userConf.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.modules.log.enums.UserConfigBaseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户个性化配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_conf")
public class SysUserConfEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 配置项
     */
    private String userId;
    /**
     * 配置项
     */
    private String k;
    /**
     * 配置项内容
     */
    private String content;

    public SysUserConfEntity() {
    }

    public SysUserConfEntity(String userId, UserConfigBaseEnum k, String content) {
        this.userId = userId;
        this.k = k.name();
        this.content = content;
    }
}
