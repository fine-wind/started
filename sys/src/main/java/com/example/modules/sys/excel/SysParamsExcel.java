package com.example.modules.sys.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * 参数管理
 *
 *
 * @since 1.0.0
 */
@Data
public class SysParamsExcel {
    @Excel(name = "参数编码")
    private String paramCode;
    @Excel(name = "参数值")
    private String paramValue;
    @Excel(name = "备注")
    private String remark;

}
