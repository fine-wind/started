package com.example.started.modules.message.bo;

import com.example.started.modules.message.sms.SmsConfig;
import com.example.common.v0.data.bo.BaseBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "短信")
public class SysSmsBo extends BaseBo implements Serializable {
    private static final long serialVersionUID = 1L;

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
