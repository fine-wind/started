package com.example.started.api.modules.param.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysParamsBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    /**
     * 参数编码
     */
    private String paramCode;
    /**
     * 参数编码
     */
    private String paramCodeEq;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 类型
     */
    private String paramType;
    /**
     * 备注
     */
    private String remark;
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
