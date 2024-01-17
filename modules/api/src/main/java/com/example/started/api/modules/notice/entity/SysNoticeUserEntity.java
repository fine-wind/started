package com.example.started.api.modules.notice.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的通知
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notice_user")
public class SysNoticeUserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 通知ID
     */
    private Long noticeId;
    /**
     * 接收者ID
     */
    private String receiverId;
    /**
     * 阅读状态  0：未读  1：已读
     */
    private Integer readStatus;
    /**
     * 阅读时间
     */
    private Date readDate;
}
