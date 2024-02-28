package com.example.started.log.log.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.log.log.entity.SysLogLoginEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginDao extends BaseDao<SysLogLoginEntity> {

}