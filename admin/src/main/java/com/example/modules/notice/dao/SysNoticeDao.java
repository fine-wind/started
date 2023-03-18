package com.example.modules.notice.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.notice.entity.SysNoticeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 通知管理
 */
@Mapper
public interface SysNoticeDao extends BaseDao<SysNoticeEntity> {
    /**
     * 获取被通知的用户列表
     *
     * @param noticeId 通知ID
     */
    List<SysNoticeEntity> getNoticeUserList(Long noticeId);

    /**
     * 获取我的通知列表
     *
     * @param receiverId 接收者ID
     */
    List<SysNoticeEntity> getMyNoticeList(String receiverId);
}
