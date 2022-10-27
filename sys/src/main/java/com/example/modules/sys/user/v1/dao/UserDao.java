package com.example.modules.sys.user.v1.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.sys.user.v1.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户
 */
@Mapper
public interface UserDao extends BaseDao<SysUserEntity> {

}
