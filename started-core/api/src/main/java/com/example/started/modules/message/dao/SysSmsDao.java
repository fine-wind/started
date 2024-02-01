package com.example.started.modules.message.dao;

import com.example.started.modules.message.entity.SysSmsEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信
 *
 *
 */
@Mapper
public interface SysSmsDao extends BaseDao<SysSmsEntity> {

}
