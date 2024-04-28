package com.example.started.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.sys.entity.SysRoleUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 */
@Mapper
public interface SysRoleUserDao extends BaseDao<SysRoleUserEntity> {

}
