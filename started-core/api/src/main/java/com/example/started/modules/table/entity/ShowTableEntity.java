package com.example.started.modules.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_table")
public class ShowTableEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 父id
     */
    private Long pid;
    private String title;
    private String name;
    private String vp;
    private String tableName;

}
