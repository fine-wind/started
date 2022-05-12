package com.example.common.data.dao;

import com.example.common.data.entity.SysGroupEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理
 */
@Mapper
public interface SysDeptDao extends BaseDao<SysGroupEntity> {

}
