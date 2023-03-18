package com.example.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单对应关系
 */
@Mapper
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {

}
