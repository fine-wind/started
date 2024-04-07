package com.example.started.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {

}
