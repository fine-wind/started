package com.example.common.v0.data.dto.sys;

import lombok.Data;
import lombok.experimental.Accessors;

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
