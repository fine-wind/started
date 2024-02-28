package com.example.started.api.modules.sys.bo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 角色
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class SysRoleBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String remark;
    /**
     * 部门ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long deptId;
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
