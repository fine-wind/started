package com.example.common.v0.modules.sys.dict.service;

import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.common.v0.modules.sys.dict.bo.SysDictTypeBo;
import com.example.common.v0.modules.sys.dict.dto.SysDictTypeDTO;
import com.example.common.v0.modules.sys.dict.entity.SysDictTypeEntity;
import com.example.common.v0.modules.sys.dict.vo.DictType;

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
