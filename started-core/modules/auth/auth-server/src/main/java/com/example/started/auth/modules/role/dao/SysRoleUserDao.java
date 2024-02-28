package com.example.started.auth.modules.role.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.auth.modules.role.entity.SysRoleUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色用户关系
 *
 * @since 1.0.0
 */
@Mapper
public interface SysRoleUserDao extends BaseDao<SysRoleUserEntity> {


}
