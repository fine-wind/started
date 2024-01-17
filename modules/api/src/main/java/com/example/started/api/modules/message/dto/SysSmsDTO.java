package com.example.started.api.modules.message.dto;

import com.example.started.api.modules.message.sms.SmsConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信
 *
 *
 */
@Data
@ApiModel(value = "短信")
public class SysSmsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "短信编码")
    private String smsCode;

    @ApiModelProperty(value = "平台类型")
    private Integer platform;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "短信配置")
    private SmsConfig config;

    @ApiModelProperty(value = "创建时间")
    private Date createDate;

}
