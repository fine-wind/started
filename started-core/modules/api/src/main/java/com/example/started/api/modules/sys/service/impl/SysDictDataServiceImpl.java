package com.example.started.api.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.started.api.modules.dict.dao.SysDictDataDao;
import com.example.started.api.modules.dict.entity.SysDictDataEntity;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.api.modules.dict.bo.SysDictDataBo;
import com.example.started.api.modules.dict.dao.SysDictDataDTO;
import com.example.started.api.modules.dict.service.SysDictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 字典类型
 */
@Service
@Primary
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataBo, SysDictDataDao, SysDictDataEntity> implements SysDictDataService {

    @Override
    public PageData<SysDictDataDTO> page(SysDictDataBo params) {
        SysDictDataEntity entity = new SysDictDataEntity();
        BeanUtils.copyProperties(params, entity);

        IPage<SysDictDataEntity> page = baseDao.selectPage(
                getPage(params, "sort", true),
                getWrapper(entity)
        );

        return getPageData(page, SysDictDataDTO.class);
    }

    @Override
    public List<SysDictDataEntity> list(SysDictDataBo params) {
        SysDictDataEntity entity = new SysDictDataEntity();
        BeanUtils.copyProperties(params, entity);
        return this.baseDao.selectList(this.getWrapper(entity));
    }

    // @Override
    public QueryWrapper<SysDictDataEntity> getWrapper(SysDictDataEntity params) {
        Long dictTypeId = params.getDictTypeId();
        String dictLabel = params.getDictLabel();
        String dictValue = params.getDictValue();

        QueryWrapper<SysDictDataEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(Objects.nonNull(dictTypeId), "dict_type_id", dictTypeId);
        wrapper.like(StringUtils.isNotBlank(dictLabel), "dict_label", dictLabel);
        wrapper.like(StringUtils.isNotBlank(dictValue), "dict_value", dictValue);

        return wrapper;
    }


    @Override
    public SysDictDataDTO get(Long id) {
        SysDictDataEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysDictDataDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);

        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDictDataDTO dto) {
        SysDictDataEntity entity = ConvertUtils.sourceToTarget(dto, SysDictDataEntity.class);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除
        deleteBatchIds(Arrays.asList(ids));
    }

}
