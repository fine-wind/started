package com.example.modules.log.service;


import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.BaseService;
import com.example.modules.log.bo.SysLogErrorBo;
import com.example.modules.log.dto.SysLogErrorDTO;
import com.example.modules.log.entity.SysLogErrorEntity;

import java.util.List;

/**
 * 异常日志
 *
 * @since 1.0.0
 */
public interface SysLogErrorService extends BaseService<SysLogErrorBo, SysLogErrorEntity> {

    PageData<SysLogErrorDTO> page(SysLogErrorBo params);

    List<SysLogErrorDTO> list(SysLogErrorBo params);

    void save(SysLogErrorEntity entity);

}
