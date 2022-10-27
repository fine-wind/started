package com.example.common.v0.data.bo.sys;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysBaseConfBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    private String item;
    private String title;
    private String content;
    /**
     * 存储的排序
     */
    private Integer sort;
}
