package com.example.started.modules.oss.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 文件上传
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_oss")
public class SysOssBo extends BaseBo {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    private String name;
    /**
     * 存储类型
     */
    private Integer type;
    /**
     * 存储地址或存储路径
     */
    private String url;

}
