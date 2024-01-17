package com.example.started.api.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.started.api.modules.dict.entity.SysDictTypeEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典类型
 */
@Mapper
public interface SysDictTypeDao extends BaseDao<SysDictTypeEntity> {

}
