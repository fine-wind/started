package com.example.common.data.modules.role.dao;

import com.example.common.data.dao.BaseDao;
import com.example.common.data.modules.role.entity.SysRoleUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色用户关系
 *
 * @since 1.0.0
 */
@Mapper
public interface SysRoleUserDao extends BaseDao<SysRoleUserEntity> {


}
