package com.example.common.v0.modules.sys.dict.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.common.v0.modules.sys.dict.entity.SysDictDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 字典数据
 */
@Mapper
public interface SysDictDataDao extends BaseDao<SysDictDataEntity> {

    /**
     * 找什么东西 在哪个地方 通过什么条件
     *
     * @param column    显示的列
     * @param tableName 表名
     * @param key       条件
     * @return keyValue 条件数据
     */
    @Select("select ${column} " +
            "from ${tableName} " +
            "where ${key} = #{keyValue}")
    String selectValueByTableAndColumn(@Param("column") String column,
                                       @Param("tableName") String tableName,
                                       @Param("key") String key,
                                       @Param("keyValue") String keyValue
    );
}