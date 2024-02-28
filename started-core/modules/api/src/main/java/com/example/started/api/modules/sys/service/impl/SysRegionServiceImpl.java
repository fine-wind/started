
package com.example.started.api.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.api.modules.sys.bo.SysRegionBo;
import com.example.started.api.modules.sys.dao.SysRegionDao;
import com.example.started.api.modules.sys.dto.SysRegionDTO;
import com.example.started.api.modules.sys.dto.region.Region;
import com.example.started.api.modules.sys.dto.region.RegionCity;
import com.example.started.api.modules.sys.dto.region.RegionProvince;
import com.example.started.api.modules.sys.entity.SysRegionEntity;
import com.example.started.api.modules.sys.enums.RegionLeafEnum;
import com.example.started.api.modules.sys.enums.RegionLevelEnum;
import com.example.started.api.modules.sys.service.SysRegionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class SysRegionServiceImpl extends BaseServiceImpl<SysRegionBo, SysRegionDao, SysRegionEntity> implements SysRegionService {

    @Override
    public List<SysRegionDTO> list(SysRegionBo params) {
        if (Objects.isNull(params.getPid())) {
            //查询一级
            params.setTreeLevel(RegionLevelEnum.ONE.value());
        }
        //查询列表
        List<SysRegionEntity> entityList = baseDao.selectList(getQueryWrapper(params));

        List<SysRegionDTO> dtoList = new ArrayList<>(entityList.size());
        for (SysRegionEntity entity : entityList) {
            SysRegionDTO dto = new SysRegionDTO();
            BeanUtils.copyProperties(entity, dto);
            dto.setHasChildren(entity.getLeaf() != 1);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public List<Map<String, Object>> getTreeList() {
        return baseDao.getTreeList();
    }

    @Override
    public SysRegionDTO get(Long id) {
        SysRegionEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysRegionDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRegionDTO dto) {
        SysRegionEntity entity = ConvertUtils.sourceToTarget(dto, SysRegionEntity.class);

        upP(entity, dto);

        //新增都是叶子节点
        entity.setLeaf(RegionLeafEnum.YES.value());
        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRegionDTO dto) {
        SysRegionEntity entity = ConvertUtils.sourceToTarget(dto, SysRegionEntity.class);

        //上级不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException(UniversalCode.SUPERIOR_REGION_ERROR);
        }

        upP(entity, dto);

        //查询下级
        Long subCount = baseDao.selectCount(this.getQueryWrapper(new SysRegionBo().setPid(dto.getId())));
        if (subCount == 0) {
            entity.setLeaf(RegionLeafEnum.YES.value());
        } else {
            entity.setLeaf(RegionLeafEnum.NO.value());
        }

        updateById(entity);
    }

    /**
     * 更新上级的数据
     *
     * @param entity 本级数据
     * @param dto    参数
     */
    private void upP(SysRegionEntity entity, SysRegionDTO dto) {
        //查询上级
        SysRegionEntity parentEntity = baseDao.selectById(dto.getPid());
        if (parentEntity == null) {
            entity.setTreeLevel(RegionLevelEnum.ONE.value());
        } else {
            entity.setTreeLevel(parentEntity.getTreeLevel() + 1);
            //上级存在，且为叶子节点，需要修改为非叶子节点
            if (parentEntity.getLeaf() == RegionLeafEnum.YES.value()) {
                parentEntity.setLeaf(RegionLeafEnum.NO.value());
                baseDao.updateById(parentEntity);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除
        baseDao.deleteById(id);
    }

    @Override
    public Long getCountByPid(Long pid) {
        return baseDao.selectCount(this.getQueryWrapper(new SysRegionBo().setPid(pid)));
    }

    @Override
    public List<RegionProvince> getRegion(boolean threeLevel) {
        List<SysRegionEntity> provinceList = baseDao.getListByLevel(RegionLevelEnum.ONE.value());
        List<SysRegionEntity> cityList = baseDao.getListByLevel(RegionLevelEnum.TWO.value());

        List<RegionProvince> provinces = ConvertUtils.sourceToTarget(provinceList, RegionProvince.class);
        List<RegionCity> cities = ConvertUtils.sourceToTarget(cityList, RegionCity.class);

        for (RegionCity city : cities) {
            for (RegionProvince province : provinces) {
                if (city.getPid().equals(province.getId())) {
                    province.getCities().add(city);
                }
            }
        }

        //无需显示3级区县
        if (!threeLevel) {
            return provinces;
        }

        List<SysRegionEntity> countyList = baseDao.getListByLevel(RegionLevelEnum.THREE.value());
        List<Region> counties = ConvertUtils.sourceToTarget(countyList, Region.class);
        for (Region county : counties) {
            for (RegionCity city : cities) {
                if (county.getPid().equals(city.getId())) {
                    city.getCounties().add(county);
                }
            }
        }

        return provinces;
    }

    @Override
    public LambdaQueryWrapper<SysRegionEntity> getQueryWrapper(SysRegionBo params) {
        LambdaQueryWrapper<SysRegionEntity> queryWrapper = super.getQueryWrapper(params);
        queryWrapper.eq(Objects.nonNull(params.getPid()), SysRegionEntity::getPid, params.getPid());
        queryWrapper.eq(Objects.nonNull(params.getTreeLevel()), SysRegionEntity::getTreeLevel, params.getTreeLevel());
        return queryWrapper;
    }
}
