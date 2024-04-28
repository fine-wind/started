package com.example.started.modules.table.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.started.modules.table.dto.SysTableDto;
import com.example.started.modules.table.entity.ShowTableEntity;
import com.example.common.v0.data.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 表Dao
 *
 * @since 1.0.0
 */
@Mapper
public interface SysTableDao extends BaseDao<ShowTableEntity> {

    int insertData(@Param("tableName") String tableName, @Param("col") List<String> col, @Param("data") List<List<String>> data);

    List<Map<String, Object>> selectTableData(@Param("table") SysTableDto sysTableDto);

    IPage<Map<String, Object>> selectTableDataPage(IPage<?> page, @Param("table") SysTableDto sysTableDto);
}
