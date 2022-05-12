package com.example.common.data.modules.log.dao;

import com.example.common.data.dao.BaseDao;
import com.example.common.data.modules.log.entity.SysLogLoginEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Mapper
public interface SysLogLoginDao extends BaseDao<SysLogLoginEntity> {

}
