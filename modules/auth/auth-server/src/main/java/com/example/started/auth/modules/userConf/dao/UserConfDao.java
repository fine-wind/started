package com.example.started.auth.modules.userConf.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.auth.modules.userConf.entity.SysUserConfEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户个性化配置
 */
@Mapper
public interface UserConfDao extends BaseDao<SysUserConfEntity> {

}
