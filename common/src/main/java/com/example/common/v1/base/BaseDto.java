package com.example.common.v1.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 公共Bo
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "公共Bo")
public class BaseDto implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 创建者
     */
    private String creator;
}
