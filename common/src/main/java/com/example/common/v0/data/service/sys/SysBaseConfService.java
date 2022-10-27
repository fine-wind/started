package com.example.common.v0.data.service.sys;

import com.example.common.v0.data.bo.sys.SysBaseConfBo;
import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.entity.SysBaseConfEntity;
import com.example.common.v0.data.service.CrudService;

import java.util.List;

/**
 * 公司
 */
public interface SysBaseConfService extends CrudService<SysBaseConfBo, SysBaseConfEntity, SysBaseConfDTO> {

    List<SysBaseConfDTO> getAll();
}
