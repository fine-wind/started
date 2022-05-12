package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统-基础默认配置
 *
 * @since 1.0.0 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_base_conf")
public class SysBaseConfEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String item;
    private String title;
    private String content;
    private Integer sort;

}
