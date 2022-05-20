package com.example.modules.log.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.data.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常日志
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogErrorBo extends BaseBo {
    private static final long serialVersionUID = 1L;

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
     * 用户代理
     */
    private String userAgent;
    /**
     * 操作IP
     */
    private String ip;
    /**
     * 异常信息
     */
    private String errorInfo;

}