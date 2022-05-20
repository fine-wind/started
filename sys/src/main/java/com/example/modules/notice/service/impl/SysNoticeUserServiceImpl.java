package com.example.modules.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.data.service.impl.BaseServiceImpl;
import com.example.modules.notice.bo.SysNoticeBo;
import com.example.modules.notice.dao.SysNoticeUserDao;
import com.example.modules.notice.entity.SysNoticeUserEntity;
import com.example.modules.notice.enums.NoticeReadStatusEnum;
import com.example.modules.notice.service.SysNoticeUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 我的通知
 */
@Service
public class SysNoticeUserServiceImpl extends BaseServiceImpl<SysNoticeBo, SysNoticeUserDao, SysNoticeUserEntity> implements SysNoticeUserService {

    @Override
    public void insertAllUser(SysNoticeUserEntity entity) {
        baseDao.insertAllUser(entity);
    }

    @Override
    public void updateReadStatus(Long receiverId, Long noticeId) {
        SysNoticeUserEntity entity = new SysNoticeUserEntity();
        entity.setReceiverId(receiverId);
        entity.setNoticeId(noticeId);
        entity.setReadStatus(NoticeReadStatusEnum.READ.value());
        entity.setReadDate(new Date());

        //标记为已读
        QueryWrapper<SysNoticeUserEntity> query = new QueryWrapper<>();
        query.eq("receiver_id", receiverId);
        query.eq("notice_id", noticeId);
        baseDao.update(entity, query);
    }

    @Override
    public Long getUnReadNoticeCount(Long receiverId) {
        return baseDao.selectCount(this.getQueryWrapper(new SysNoticeBo().setReceiverId(receiverId).setReadStatus(NoticeReadStatusEnum.UNREAD.value())));
    }

    @Override
    public LambdaQueryWrapper<SysNoticeUserEntity> getQueryWrapper(SysNoticeBo params) {
        LambdaQueryWrapper<SysNoticeUserEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getReadStatus()), SysNoticeUserEntity::getReadDate, params.getReadStatus());
        queryWrapper.eq(Objects.nonNull(params.getReceiverId()), SysNoticeUserEntity::getReceiverId, params.getReceiverId());

        return queryWrapper;
    }
}