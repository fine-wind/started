package com.example.common.data.bo.sys;

import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

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
