package com.example.started.api.modules.dict.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 数据字典
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysDictDataBo extends BaseBo {
    private static final long serialVersionUID = 1L;
    /**
     * 字典类型ID
     */
    private Long dictTypeId;
    /**
     * 字典标签
     */
    private String dictLabel;
    /**
     * 字典值
     */
    private String dictValue;
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
