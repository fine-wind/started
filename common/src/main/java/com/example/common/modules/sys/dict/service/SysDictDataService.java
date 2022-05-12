package com.example.common.modules.sys.dict.service;

import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.common.modules.sys.dict.bo.SysDictDataBo;
import com.example.common.modules.sys.dict.dao.SysDictDataDTO;
import com.example.common.modules.sys.dict.entity.SysDictDataEntity;

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
