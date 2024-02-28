package com.example.started.api.modules.notice.bo;

import com.example.started.api.modules.notice.enums.NoticeReadStatusEnum;
import com.example.common.v0.data.bo.BaseBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 通知管理
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "通知管理")
@EqualsAndHashCode(callSuper = true)
public class SysNoticeBo extends BaseBo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "通知类型")
    private Integer type;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "接收者类型  0：全部  1：部门")
    private Integer receiverType;
    @ApiModelProperty(value = "接收者ID，用逗号分开")
    private String receiverTypeIds;
    @ApiModelProperty(value = "接收者ID列表")
    private List<Long> receiverTypeList;
    @ApiModelProperty(value = "发送状态  0：草稿  1：已发布")
    private Integer status;
    @ApiModelProperty(value = "发送者")
    private String senderName;
    @ApiModelProperty(value = "发送时间")
    private Date senderDate;
    @ApiModelProperty(value = "创建者")
    private Long creator;
    @ApiModelProperty(value = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "接收者id")
    private String receiverId;
    @ApiModelProperty(value = "接收者")
    private String receiverName;
    @ApiModelProperty(value = "阅读时间")
    private Date readDate;
    /**
     * @see NoticeReadStatusEnum
     */
    @ApiModelProperty(value = "阅读状态")
    private Integer readStatus;
}
