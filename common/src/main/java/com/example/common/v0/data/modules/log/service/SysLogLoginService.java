package com.example.common.v0.data.modules.log.service;

import com.example.common.v0.data.modules.log.bo.SysLogLoginBo;
import com.example.common.v0.data.modules.log.dto.SysLogLoginDTO;
import com.example.common.v0.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v1.base.service.BaseService;

import java.util.List;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
public interface SysLogLoginService extends BaseService<SysLogLoginBo, SysLogLoginEntity> {

    List<SysLogLoginDTO> list(SysLogLoginBo params);

    void save(SysLogLoginEntity entity);

    PageData<SysLogLoginDTO> page(SysLogLoginBo params);

    /**
     * @return 获取访问次数
     */
    Long getVisitCount();


    SysLogLoginEntity getEntity();
}
