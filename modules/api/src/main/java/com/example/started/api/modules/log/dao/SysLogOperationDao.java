package com.example.started.api.modules.log.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.api.modules.log.entity.SysLogOperationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Mapper
public interface SysLogOperationDao extends BaseDao<SysLogOperationEntity> {

}
