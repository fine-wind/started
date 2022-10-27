package com.example.modules.param.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.modules.params.redis.SysParamsRedis;
import com.example.common.sys.param.entity.SysParamsEntity;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.param.bo.SysParamsBo;
import com.example.modules.param.dao.SysParamsDao;
import com.example.modules.param.dto.SysParamsDTO;
import com.example.modules.param.service.SysParamsService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.CONF_MAP;
import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
@Log4j2
@Service
public class SysParamsServiceImpl extends BaseServiceImpl<SysParamsBo, SysParamsDao, SysParamsEntity> implements SysParamsService {
    private final SysParamsRedis sysParamsCache;

    public SysParamsServiceImpl(SysParamsRedis sysParamsCache) {
        this.sysParamsCache = sysParamsCache;
    }

    public void loadAllParam() {
        List<SysParamsDTO> list = list(new SysParamsBo());
        Constant.PARAM_CONF.APP_SETTINGS_CONF.init();
        list.forEach(e -> {
            Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(e.getParamCode());
            if (Objects.isNull(kvr)) {
                return;
            }
            kvr.setValue(e.getParamValue());

            sysParamsCache.set(e.getParamCode(), e.getParamValue());
        });

        /* 网站配置 */
        CONF_MAP.forEach((code, kvr) -> {
            SysParamsDTO sysParamsDTO = list.stream().filter(e -> kvr.getCode().equals(e.getParamCode())).findAny().orElse(null);
            if (sysParamsDTO == null) {
                SysParamsDTO dto = new SysParamsDTO();
                dto.setParamCode(code);
                dto.setParamValue(kvr.getValue());
                dto.setParamType(Constant.PARAM_CONF.CONF_TYPE.SYS);
                dto.setRemark(kvr.getRemark());
                this.save(dto);
            }
        });

    }

    @Override
    public LambdaQueryWrapper<SysParamsEntity> getQueryWrapper(SysParamsBo params) {

        LambdaQueryWrapper<SysParamsEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getParamType()), SysParamsEntity::getParamType, params.getParamType());
        queryWrapper.eq(StringUtils.isNotBlank(params.getParamCodeEq()), SysParamsEntity::getParamCode, params.getParamCodeEq());

        queryWrapper.like(StringUtils.isNotBlank(params.getParamCode()), SysParamsEntity::getParamCode, params.getParamCode());
        queryWrapper.like(StringUtils.isNotBlank(params.getRemark()), SysParamsEntity::getRemark, params.getRemark());

        return queryWrapper;
    }

    @Override
    public PageData<SysParamsDTO> page(SysParamsBo params) {

        IPage<SysParamsEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getQueryWrapper(params)
        );

        return getPageData(page, SysParamsDTO.class);
    }

    @Override
    public List<SysParamsDTO> list(SysParamsBo params) {
        List<SysParamsEntity> entityList = baseDao.selectList(getQueryWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysParamsDTO.class);
    }

    @Override
    public SysParamsDTO get(Long id) {
        SysParamsEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysParamsDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysParamsDTO dto) {
        SysParamsEntity entity = ConvertUtils.sourceToTarget(dto, SysParamsEntity.class);

        Objects.requireNonNull(entity.getParamType());

        try {
            insert(entity);
        } catch (DuplicateKeyException ignored) {

        }

        sysParamsCache.set(entity.getParamCode(), entity.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysParamsDTO dto) {
        SysParamsEntity entity = ConvertUtils.sourceToTarget(dto, SysParamsEntity.class);
        updateById(entity);

        sysParamsCache.set(entity.getParamCode(), entity.getParamValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        //删除Redis数据
        List<String> paramCodeList = baseDao.getParamCodeList(ids);
        String[] paramCodes = paramCodeList.toArray(new String[0]);
        sysParamsCache.delete(paramCodes);

        //删除
        deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 根据参数编码，获取参数的value值
     *
     * @param paramCode 参数编码
     */
    @Override
    public String getValue(String paramCode) {
        String paramValue = sysParamsCache.get(paramCode);
        if (paramValue == null) {
            SysParamsBo bo = new SysParamsBo();
            bo.setParamCodeEq(paramCode);
            SysParamsEntity one = baseDao.selectOne(getQueryWrapper(bo).orderByDesc(SysParamsEntity::getCreateDate));
            if (Objects.nonNull(one)) {
                paramValue = one.getParamValue();
            }
            sysParamsCache.set(paramCode, Objects.toString(paramValue, ""));
        }
        return paramValue;
    }

    @Override
    public Map<String, String> getAppSettings() {
        Map<String, String> appSettings = new HashMap<>(CONF_MAP.size());
        CONF_MAP.forEach((code, kvr) -> {
            String value = this.getValue(code);
            value = Objects.toString(value, kvr.getValue());
            appSettings.put(code, value);
        });
        return appSettings;
    }

    @Override
    public <T> T getValueObject(String paramCode, Class<T> clazz) {
        String paramValue = getValue(paramCode);
        if (StringUtils.isNotBlank(paramValue)) {
            return JSON.parseObject(paramValue, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ServerException(UniversalCode.PARAMS_GET_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByCode(String paramCode, String paramValue) {
        baseDao.updateValueByCode(paramCode, paramValue);
        sysParamsCache.set(paramCode, paramValue);
    }

    /**
     * 清除并重新加载缓存
     */
    @Override
    public void clear() {
        log.info("清除并重新加载缓存");
        sysParamsCache.clear();
        this.loadAllParam();
    }

}
