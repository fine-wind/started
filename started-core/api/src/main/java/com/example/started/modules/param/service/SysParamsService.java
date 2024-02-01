package com.example.started.modules.param.service;

import com.example.started.modules.param.bo.SysParamsBo;
import com.example.started.modules.param.dto.SysParamsDTO;
import com.example.started.modules.param.entity.SysParamsEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
public interface SysParamsService extends BaseService<SysParamsBo, SysParamsEntity> {

    PageData<SysParamsDTO> page(SysParamsBo params);

    List<SysParamsDTO> list(SysParamsBo params);

    SysParamsDTO get(Long id);

    void save(SysParamsDTO dto);

    void update(SysParamsDTO dto);

    void delete(Long[] ids);

    /**
     * 根据参数编码，获取参数的value值
     *
     * @param paramCode 参数编码
     */
    String getValue(String paramCode);

    Map<String, String> getAppSettings();

    /**
     * 根据参数编码，获取value的Object对象
     *
     * @param paramCode 参数编码
     * @param clazz     Object对象
     */
    <T> T getValueObject(String paramCode, Class<T> clazz);

    /**
     * 根据参数编码，更新value
     *
     * @param paramCode  参数编码
     * @param paramValue 参数值
     */
    void updateValueByCode(String paramCode, String paramValue);

    /**
     * 清除已有缓存
     */
    void clear();
}
