package com.example.common.modules.sys.dict.service;

import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.common.modules.sys.dict.bo.SysDictTypeBo;
import com.example.common.modules.sys.dict.dto.SysDictTypeDTO;
import com.example.common.modules.sys.dict.vo.DictType;
import com.example.modules.sys.dict.entity.SysDictTypeEntity;

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