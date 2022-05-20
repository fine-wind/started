package com.example.modules.sys.service;

import com.example.common.data.service.BaseService;
import com.example.modules.sys.bo.SysRegionBo;
import com.example.modules.sys.dto.SysRegionDTO;
import com.example.modules.sys.dto.region.RegionProvince;
import com.example.modules.sys.entity.SysRegionEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域
 */
public interface SysRegionService extends BaseService<SysRegionBo, SysRegionEntity> {

    List<SysRegionDTO> list(SysRegionBo params);

    List<Map<String, Object>> getTreeList();

    SysRegionDTO get(Long id);

    void save(SysRegionDTO dto);

    void update(SysRegionDTO dto);

    void delete(Long id);

    Long getCountByPid(Long pid);

    List<RegionProvince> getRegion(boolean threeLevel);
}
