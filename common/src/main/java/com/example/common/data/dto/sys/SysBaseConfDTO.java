package com.example.common.data.dto.sys;

import com.example.common.utils.StringUtil;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 注册表单
 */
@Data
//@ApiModel(value = "公司DTO")
@Accessors(chain = true)
public class SysBaseConfDTO {

    private Long id;
    private String item;
    private String title;
    private String content;
    private Integer sort;

}
