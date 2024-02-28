package com.example.started.api.modules.log.log.service;

import com.example.started.api.modules.log.log.bo.SysLogLoginBo;
import com.example.started.api.modules.log.log.dto.SysLogLoginDTO;
import com.example.started.api.modules.log.log.entity.SysLogLoginEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v1.base.service.BaseServiceV1;

import java.util.List;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
public interface SysLogLoginService extends BaseServiceV1<SysLogLoginBo, SysLogLoginEntity> {

    List<SysLogLoginDTO> list(SysLogLoginBo params);

    void save(SysLogLoginEntity entity);

    PageData<SysLogLoginDTO> page(SysLogLoginBo params);

    /**
     * @return 获取访问次数
     */
    Long getVisitCount();


    SysLogLoginEntity getEntity();
}
