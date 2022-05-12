package com.example.common.data.modules.log.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_login")
public class SysLogLoginBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    /**
     * 用户操作   0：用户登录   1：用户退出
     */
    private Integer operation;
    /**
     * 状态  0：失败    1：成功    2：账号已锁定
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
