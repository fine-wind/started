package com.example.started.api.modules.log.service;

import com.example.started.api.modules.log.bo.SysLogOperationBo;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.started.api.modules.log.dto.SysLogOperationDTO;
import com.example.started.api.modules.log.entity.SysLogOperationEntity;

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
