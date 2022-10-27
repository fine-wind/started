package com.example.modules.sys.dao;

import com.example.common.v0.data.dao.BaseDao;
import com.example.modules.sys.entity.SysRegionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 */
@Mapper
public interface SysRegionDao extends BaseDao<SysRegionEntity> {

    List<SysRegionEntity> getListByLevel(Integer treeLevel);

    List<Map<String, Object>> getTreeList();
}
