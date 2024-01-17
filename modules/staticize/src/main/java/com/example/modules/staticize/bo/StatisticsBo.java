package com.example.modules.staticize.bo;

import com.example.common.v0.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StatisticsBo extends BaseBo {

    /**
     * 用户操作   0：用户登录   1：用户退出   2：访问主页面
     */
    private Integer operation;
    /**
     * 状态  0：失败    1：成功
     */
    private Integer status;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 用户名
     */
    private String creatorName;

}
