package com.example.started.modules.table.dao;

import com.example.started.modules.table.entity.ShowTableEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表Dao
 *
 * @since 1.0.0
 */
@Mapper
public interface SysTableDao extends BaseDao<ShowTableEntity> {

    int insertData(@Param("tableName") String tableName, @Param("col") List<String> col, @Param("data") List<List<String>> data);
}
