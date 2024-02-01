package com.example.started.modules.notice.service;

import com.example.common.v0.data.service.BaseService;
import com.example.started.modules.notice.bo.SysNoticeBo;
import com.example.started.modules.notice.entity.SysNoticeUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 我的通知
 */
public interface SysNoticeUserService extends BaseService<SysNoticeBo, SysNoticeUserEntity> {

    /**
     * 通知全部用户
     */
    void insertAllUser(SysNoticeUserEntity entity);

    /**
     * 标记我的通知为已读
     *
     * @param receiverId 接收者ID
     * @param noticeId   通知ID
     */
    void updateReadStatus(@Param("receiverId") String receiverId, @Param("noticeId") Long noticeId);


    /**
     * 未读的通知数
     *
     * @param receiverId 接收者ID
     */
    Long getUnReadNoticeCount(String receiverId);
}
