package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分组管理
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_group")
public class SysGroupEntity extends BaseEntity {
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
