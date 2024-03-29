package com.example.common.v0.data.bo.sys;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 用户
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysBaseConfBo extends BaseBo {
    @Serial
    private static final long serialVersionUID = 1L;

    private String item;
    private String title;
    private String content;
    /**
     * 存储的排序
     */
    private Integer sort;
}
