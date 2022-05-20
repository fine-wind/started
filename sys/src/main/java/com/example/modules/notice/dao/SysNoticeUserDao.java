package com.example.modules.notice.dao;

import com.example.common.data.dao.BaseDao;
import com.example.modules.notice.entity.SysNoticeUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 我的通知
 */
@Mapper
public interface SysNoticeUserDao extends BaseDao<SysNoticeUserEntity> {
    /**
     * 通知全部用户
     */
    @Insert("insert into sys_notice_user(notice_id, receiver_id, read_status)" +
            "select #{noticeId}, t1.id, #{readStatus} from sys_user t1")
    void insertAllUser(SysNoticeUserEntity entity);

}
