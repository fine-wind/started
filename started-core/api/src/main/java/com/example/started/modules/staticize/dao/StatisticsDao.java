package com.example.started.modules.staticize.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.staticize.entity.StatisticsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Mapper
public interface StatisticsDao extends BaseDao<StatisticsEntity> {

}
