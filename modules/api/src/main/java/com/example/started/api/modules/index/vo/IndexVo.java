package com.example.started.api.modules.index.vo;

import com.example.common.v0.tr.TrVo;
import com.example.common.v1.annotation.TiField;
import com.example.started.api.modules.param.entity.SysParamsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IndexVo extends TrVo {
    /**
     * 系统启动时间
     */
    private String startTime;
    /**
     * 网站域名
     */
    private String bindingDomainName;
    /*网站名称*/
    private String name;
    /**
     * 网站名称简称
     */
    @TiField(table = SysParamsEntity.class, key = "param_value")
    private String shortName;
    /**
     * 版权配置
     */
    private String copyright;
    /**
     * 验证码配置
     */
    private boolean captcha;
    /**
     * 是否初始化
     */
    private boolean init;
    /**
     * 注册用户
     */
    private boolean register;

}
