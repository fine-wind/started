package com.example.started.api.modules.table.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import com.example.started.api.modules.table.vo.edit.ShowEditFieldVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_table_field")
public class ShowTableFieldEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;

    private String tableId;
    private String title;
    /**
     * 传参名字
     */
    private String name;
    /**
     * 数据库字段
     */
    private String field;
    /**
     * todo 字段默认值
     */
    private String defaultValue;

    /**
     * todo 字段数据类型
     */
    private String type;

    /**
     * todo 字段校验格式
     */
    private String verify;

    public ShowEditFieldVo toEditVo() {
        ShowEditFieldVo vo = new ShowEditFieldVo();
        vo.setTitle(this.getTitle());
        vo.setName(this.getName());
        vo.setType(this.getVerify());
        return vo;
    }
}
