package com.example.started.api.modules.message.dao;

import com.example.started.api.modules.message.entity.SysSmsLogEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信日志
 *
 *
 */
@Mapper
public interface SysSmsLogDao extends BaseDao<SysSmsLogEntity> {

}
