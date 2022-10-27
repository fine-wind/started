package com.example.common.v1.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.common.v0.constant.Constant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类，所有实体都需要继承
 */
@Data
public class BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updater;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateDate;

    /**
     * 删除标志
     */
    @TableLogic(value = Constant.SysDel.DEL_NO, delval = Constant.SysDel.DEL_YES)
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
}
