package com.example.modules.log.service;

import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.modules.log.bo.SysLogOperationBo;
import com.example.modules.log.dto.SysLogOperationDTO;
import com.example.modules.log.entity.SysLogOperationEntity;

import java.util.List;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
public interface SysLogOperationService extends BaseService<SysLogOperationBo, SysLogOperationEntity> {

    PageData<SysLogOperationDTO> page(SysLogOperationBo params);

    List<SysLogOperationDTO> list(SysLogOperationBo params);

    void save(SysLogOperationEntity entity);
}
