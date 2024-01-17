package com.example.started.auth.modules.userConf.service;

import com.example.common.v0.data.bo.sys.SysBaseConfBo;
import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.entity.SysConfBaseEntity;
import com.example.common.v0.data.service.CrudService;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

/**
 * 公司
 */
public interface SysBaseConfService extends CrudService<SysBaseConfBo, SysConfBaseEntity, SysBaseConfDTO>, CommandLineRunner {

    List<SysBaseConfDTO> getAll();
}
