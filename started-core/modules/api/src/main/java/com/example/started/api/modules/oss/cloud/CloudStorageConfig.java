package com.example.started.api.modules.oss.cloud;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.example.common.v0.validator.group.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 */
@Data
@ApiModel(value = "云存储配置信息")
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型 1：七牛  2：阿里云  3：腾讯云   4：FastDFS   5：本地上传   6：MinIO")
    // todo @Range(min = 1, max = 6, message = "{oss.type.range}")
    private Integer type;

    @ApiModelProperty(value = "绑定的域名")
    @NotBlank(message = "{qiniu.domain.require}", groups = QiniuGroup.class)
    // todo @URL(message = "{qiniu.domain.url}", groups = QiniuGroup.class)
    private String domain;

    @ApiModelProperty(value = "本地上传存储目录")
    @NotBlank(message = "{local.path.url}", groups = LocalGroup.class)
    private String localPath;

    @ApiModelProperty(value = "上传路径前缀")
    private String prefix;

    // region 对象存储服务配置
    @ApiModelProperty(value = "accessKey")
    @NotBlank(message = "{minio.accesskey.require}", groups = MinioGroup.class)
    private String accessKey;

    @ApiModelProperty(value = "secretKey")
    @NotBlank(message = "{minio.secretkey.require}", groups = MinioGroup.class)
    private String secretKey;
    @ApiModelProperty(value = "存储桶名")
    @NotBlank(message = "{minio.bucketname.require}", groups = MinioGroup.class)
    private String bucketName;


    @ApiModelProperty(value = "阿里云EndPoint")
    @NotBlank(message = "{aliyun.endPoint.require}", groups = AliyunGroup.class)
    private String endPoint;

    // endregion

    @ApiModelProperty(value = "腾讯云AppId")
    @NotNull(message = "{qcloud.appid.require}", groups = QcloudGroup.class)
    private Integer qcloudAppId;

    @ApiModelProperty(value = "腾讯云SecretId")
    @NotBlank(message = "{qcloud.secretId.require}", groups = QcloudGroup.class)
    private String qcloudSecretId;


    @ApiModelProperty(value = "腾讯云COS所属地区")
    @NotBlank(message = "{qcloud.region.require}", groups = QcloudGroup.class)
    private String qcloudRegion;


}
