package com.example.common.data.service.sys;

import com.example.common.data.bo.sys.SysBaseConfBo;
import com.example.common.data.dto.sys.SysBaseConfDTO;
import com.example.common.data.entity.SysBaseConfEntity;
import com.example.common.data.service.CrudService;

import java.util.List;

/**
 * 公司
 */
public interface SysBaseConfService extends CrudService<SysBaseConfBo, SysBaseConfEntity, SysBaseConfDTO> {

    List<SysBaseConfDTO> getAll();
}
