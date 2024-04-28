package com.example.started.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.modules.sys.entity.SysUserConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户配置
 */
@Mapper
public interface SysUserConfigDao extends BaseDao<SysUserConfigEntity> {

}
