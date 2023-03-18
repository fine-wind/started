package com.example.modules.sys.userConf.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.sys.userConf.entity.SysUserConfEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户个性化配置
 */
@Mapper
public interface UserConfDao extends BaseDao<SysUserConfEntity> {

}
