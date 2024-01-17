package com.example.started.api.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.started.api.modules.dict.entity.SysDictDataEntity;
import com.example.started.api.modules.dict.entity.SysDictTypeEntity;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.api.modules.dict.bo.SysDictDataBo;
import com.example.started.api.modules.dict.bo.SysDictTypeBo;
import com.example.started.api.modules.sys.dao.SysDictTypeDao;
import com.example.started.api.modules.dict.dto.SysDictTypeDTO;
import com.example.common.v0.data.dto.DictData;
import com.example.started.api.modules.dict.vo.DictType;
import com.example.started.api.modules.dict.service.SysDictDataService;
import com.example.started.api.modules.dict.service.SysDictTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 字典类型
 */
@Service
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeBo, SysDictTypeDao, SysDictTypeEntity> implements SysDictTypeService {
    @Autowired
    private SysDictDataService sysDictDataDao;

    @Override
    public LambdaQueryWrapper<SysDictTypeEntity> getQueryWrapper(SysDictTypeBo params) {
        LambdaQueryWrapper<SysDictTypeEntity> queryWrapper = super.getQueryWrapper(params);

        String dictType = params.getDictType();
        String dictName = params.getDictName();

        queryWrapper.like(StringUtils.isNotBlank(dictType), SysDictTypeEntity::getDictType, dictType);
        queryWrapper.like(StringUtils.isNotBlank(dictName), SysDictTypeEntity::getDictName, dictName);
        return queryWrapper;
    }

    @Override
    public PageData<SysDictTypeDTO> page(SysDictTypeBo params) {
        IPage<SysDictTypeEntity> page = baseDao.selectPage(
                getPage(params, "sort", true),
                getQueryWrapper(params)
        );

        return getPageData(page, SysDictTypeDTO.class);
    }

    @Override
    public SysDictTypeDTO get(Long id) {
        SysDictTypeEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysDictTypeDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictTypeDTO dto) {
        SysDictTypeEntity entity = ConvertUtils.sourceToTarget(dto, SysDictTypeEntity.class);

        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictTypeDTO dto) {
        SysDictTypeEntity entity = ConvertUtils.sourceToTarget(dto, SysDictTypeEntity.class);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除
        deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    @Cacheable(value = "dictType", key = "#methodName")
    public List<DictType> getAllList() {
        List<SysDictTypeEntity> sysDictTypeEntities = baseDao.selectList(this.getQueryWrapper(new SysDictTypeBo()));

        List<DictType> typeList = new ArrayList<>(sysDictTypeEntities.size());
        Map<Long, DictType> typeMap = new HashMap<>(sysDictTypeEntities.size());
        for (SysDictTypeEntity item : sysDictTypeEntities) {
            DictType vo = new DictType();
            BeanUtils.copyProperties(item, vo);

            if (!typeMap.containsKey(vo.getId())) {
                typeMap.put(vo.getId(), vo);
            }
            typeList.add(vo);
        }
        List<SysDictDataEntity> dataList = sysDictDataDao.list(new SysDictDataBo());
        for (SysDictDataEntity data : dataList) {
            DictType type = typeMap.get(data.getDictTypeId());
            if (Objects.nonNull(type)) {
                if (Objects.isNull(type.getDataList())) {
                    type.setDataList(new ArrayList<>());
                }
                DictData t = new DictData();
                BeanUtils.copyProperties(data, t);
                type.getDataList().add(t);
            }
        }
        return typeList;
    }

}
