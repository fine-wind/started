package com.example.generator.modules.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * MySQL代码生成器
 */
@Mapper
public interface MySQLGeneratorDao extends GeneratorDao {
    List<Map<String, Object>> queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
