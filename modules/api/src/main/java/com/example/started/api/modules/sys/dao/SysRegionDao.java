package com.example.started.api.modules.sys.dao;

import com.example.started.api.modules.sys.entity.SysRegionEntity;
import com.example.common.v0.data.dao.BaseDao;
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
