package com.example.common.v0.data.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部门管理
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDeptBo extends BaseBo {

    /**
     * 上级ID
     */
    private Long pid;
    /**
     * 所有上级ID，用逗号分开
     */
    private String pids;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * 上级部门名称
     */
    private String parentName;

    private List<Long> deptIdList;

}
