package com.example.started.api.modules.dict.service;

import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.started.api.modules.dict.bo.SysDictTypeBo;
import com.example.started.api.modules.dict.dto.SysDictTypeDTO;
import com.example.started.api.modules.dict.entity.SysDictTypeEntity;
import com.example.started.api.modules.dict.vo.DictType;

import java.util.List;

/**
 * 数据字典
 */
public interface SysDictTypeService extends BaseService<SysDictTypeBo, SysDictTypeEntity> {

    PageData<SysDictTypeDTO> page(SysDictTypeBo params);

    SysDictTypeDTO get(Long id);

    void save(SysDictTypeDTO dto);

    void update(SysDictTypeDTO dto);

    void delete(Long[] ids);

    /**
     * 获取所有字典
     */
    List<DictType> getAllList();

}
