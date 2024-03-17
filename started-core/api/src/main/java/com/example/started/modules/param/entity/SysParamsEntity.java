package com.example.started.modules.param.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.common.v1.annotation.Ti;
import com.example.started.modules.index.vo.IndexVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_params")
public class SysParamsEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数编码
     */
    @Ti(column = IndexVo.class)
    private String paramCode;
    /**
     * 参数值
     */
    @Ti(column = IndexVo.class)
    private String paramValue;
    /**
     * 类型
     *
     * @see com.example.common.v0.constant.Constant.PARAM_CONF.CONF_TYPE
     */
    private String paramType;
    /**
     * 备注
     */
    @Ti(column = IndexVo.class)
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
