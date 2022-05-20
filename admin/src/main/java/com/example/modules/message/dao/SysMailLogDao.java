package com.example.modules.message.dao;

import com.example.common.data.dao.BaseDao;
import com.example.modules.message.entity.SysMailLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮件发送记录
 *
 *
 */
@Mapper
public interface SysMailLogDao extends BaseDao<SysMailLogEntity> {

}
