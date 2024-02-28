package com.example.started.api.modules.message.sms;

import com.example.common.v0.validator.group.AliyunGroup;
import com.example.common.v0.validator.group.QcloudGroup;
import com.example.common.v0.validator.group.QiniuGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 短信配置信息
 */
@Data
@ApiModel(value = "短信配置信息")
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "阿里云AccessKeyId")
    @NotBlank(message = "{aliyun.accesskeyid.require}", groups = AliyunGroup.class)
    private String aliyunAccessKeyId;

    @ApiModelProperty(value = "阿里云AccessKeySecret")
    @NotBlank(message = "{aliyun.accesskeysecret.require}", groups = AliyunGroup.class)
    private String aliyunAccessKeySecret;

    @ApiModelProperty(value = "阿里云短信签名")
    @NotBlank(message = "{aliyun.signname.require}", groups = AliyunGroup.class)
    private String aliyunSignName;

    @ApiModelProperty(value = "阿里云短信模板")
    @NotBlank(message = "{aliyun.templatecode.require}", groups = AliyunGroup.class)
    private String aliyunTemplateCode;

    @ApiModelProperty(value = "腾讯云AppId")
    @NotNull(message = "{qcloud.appid.require}", groups = QcloudGroup.class)
    private Integer qcloudAppId;

    @ApiModelProperty(value = "腾讯云AppKey")
    @NotBlank(message = "{qcloud.appkey.require}", groups = QcloudGroup.class)
    private String qcloudAppKey;

    @ApiModelProperty(value = "腾讯云短信签名")
    @NotBlank(message = "{qcloud.signname.require}", groups = QcloudGroup.class)
    private String qcloudSignName;

    @ApiModelProperty(value = "腾讯云短信模板ID")
    @NotBlank(message = "{qcloud.templateid.require}", groups = QcloudGroup.class)
    private String qcloudTemplateId;

    @ApiModelProperty(value = "七牛accesskey")
    @NotNull(message = "{qiniu.accesskey.require}", groups = QiniuGroup.class)
    private String qiniuAccessKey;

    @ApiModelProperty(value = "七牛SecretKey")
    @NotBlank(message = "{qiniu.secretkey.require}", groups = QiniuGroup.class)
    private String qiniuSecretKey;

    @ApiModelProperty(value = "七牛短信模板ID")
    @NotBlank(message = "{qiniu.templateId.require}", groups = QiniuGroup.class)
    private String qiniuTemplateId;
}
