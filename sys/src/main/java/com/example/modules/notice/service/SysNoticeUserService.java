package com.example.modules.notice.service;

import com.example.common.data.service.BaseService;
import com.example.modules.notice.bo.SysNoticeBo;
import com.example.modules.notice.entity.SysNoticeUserEntity;
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
    void updateReadStatus(@Param("receiverId") Long receiverId, @Param("noticeId") Long noticeId);


    /**
     * 未读的通知数
     *
     * @param receiverId 接收者ID
     */
    Long getUnReadNoticeCount(Long receiverId);
}
