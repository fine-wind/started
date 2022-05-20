package com.example.modules.log.bo;

import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogOperationBo extends BaseBo {
    private static final long serialVersionUID = 1L;

    /**
     * 用户操作
     */
    private String operation;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求参数
     */
    private String requestParams;
    /**
     * 请求时长(毫秒)
     */
    private Integer requestTime;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 状态  0：失败   1：成功
     */
    private Integer status;
    /**
     * 用户名
     */
    private String creatorName;
}
