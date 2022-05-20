package com.example.modules.log.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 操作日志
 *
 *
 * @since 1.0.0
 */
@Data
public class SysLogOperationExcel {
    @Excel(name = "用户操作")
    private String operation;
    @Excel(name = "请求URI")
    private String requestUri;
    @Excel(name = "请求方式")
    private String requestMethod;
    @Excel(name = "请求参数")
    private String requestParams;
    @Excel(name = "请求时长(毫秒)")
    private Integer requestTime;
    @Excel(name = "User-Agent")
    private String userAgent;
    @Excel(name = "操作IP")
    private String ip;
    @Excel(name = "状态", replace = {"失败_0", "成功_1"})
    private Integer status;
    @Excel(name = "用户名")
    private String creatorName;
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

}
