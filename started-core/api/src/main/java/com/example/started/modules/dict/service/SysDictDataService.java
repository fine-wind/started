package com.example.started.modules.dict.service;

import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.started.modules.dict.bo.SysDictDataBo;
import com.example.started.modules.dict.dto.SysDictDataDTO;
import com.example.started.modules.dict.entity.SysDictDataEntity;

import java.util.List;

/**
 * 数据字典
 */
public interface SysDictDataService extends BaseService<SysDictDataBo, SysDictDataEntity> {

    PageData<SysDictDataDTO> page(SysDictDataBo params);

    List<SysDictDataEntity> list(SysDictDataBo params);

    SysDictDataDTO get(Long id);

    void save(SysDictDataDTO dto);

    void update(SysDictDataDTO dto);

    void delete(Long[] ids);

}
