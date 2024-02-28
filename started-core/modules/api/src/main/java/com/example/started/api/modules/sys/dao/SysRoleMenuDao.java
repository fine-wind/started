package com.example.started.api.modules.sys.dao;

import com.example.started.api.modules.sys.entity.SysRoleMenuEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

}
