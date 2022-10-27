package com.example.modules.master.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.master.entity.SysSuperUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 */
@Mapper
public interface MasterUserDao extends BaseDao<SysSuperUserEntity> {

}
