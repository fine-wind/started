package com.example.started.modules.message.dao;

import com.example.started.modules.message.entity.SysMailLogEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送记录
 *
 *
 */
@Mapper
public interface SysMailLogDao extends BaseDao<SysMailLogEntity> {

}
