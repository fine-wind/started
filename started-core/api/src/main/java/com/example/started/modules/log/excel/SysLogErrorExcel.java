package com.example.started.modules.log.excel;

import com.example.started.api.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 异常日志
 *
 * @since 1.0.0
 */
@Data
public class SysLogErrorExcel {
    @Excel(name = "请求URI")
    private String requestUri;
    @Excel(name = "请求方式")
    private String requestMethod;
    @Excel(name = "请求参数")
    private String requestParams;
    @Excel(name = "User-Agent")
    private String userAgent;
    @Excel(name = "操作IP")
    private String ip;
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
