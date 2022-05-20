package com.example.modules.message.dao;

import com.example.common.data.dao.BaseDao;
import com.example.modules.message.entity.SysSmsLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信日志
 *
 *
 */
@Mapper
public interface SysSmsLogDao extends BaseDao<SysSmsLogEntity> {

}
