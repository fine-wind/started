package com.example.started.auth.modules.sys.group;

import com.baomidou.mybatisplus.annotation.*;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 分组管理
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_group")
public class SysGroupEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

}
