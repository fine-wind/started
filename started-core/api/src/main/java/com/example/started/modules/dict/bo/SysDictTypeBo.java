package com.example.started.modules.dict.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 字典类型
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDictTypeBo extends BaseBo {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
}
