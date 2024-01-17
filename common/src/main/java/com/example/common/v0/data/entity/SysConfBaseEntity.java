package com.example.common.v0.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 系统-基础默认配置
 *
 * @since 1.0.0 2021-04-01
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("sys_base_conf")
public class SysConfBaseEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String code;
    /**
     * 默认值
     */
    private String content;
    private Integer sort;

}
