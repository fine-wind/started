package com.example.started.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_conf")
public class SysUserConfigEntity extends BaseEntity {

    private Long userId;
    /**
     * @see Constant.UserConfigItemEnum
     */
    private Integer item;
    private String content;
}
