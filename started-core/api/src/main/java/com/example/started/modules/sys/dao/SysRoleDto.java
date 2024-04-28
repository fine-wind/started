package com.example.started.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色管理
 */
@Mapper
public interface SysRoleDto extends BaseDao<SysRoleEntity> {

}
