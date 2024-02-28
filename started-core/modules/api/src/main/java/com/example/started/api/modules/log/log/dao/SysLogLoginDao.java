package com.example.started.api.modules.log.log.dao;

import com.example.started.api.modules.log.log.entity.SysLogLoginEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginDao extends BaseDao<SysLogLoginEntity> {

}
