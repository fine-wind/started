package com.example.modules.sys.user.dao;

import com.example.common.data.dao.BaseDao;
import com.example.modules.sys.user.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户
 */
@Mapper
public interface UserDao extends BaseDao<SysUserEntity> {

}
