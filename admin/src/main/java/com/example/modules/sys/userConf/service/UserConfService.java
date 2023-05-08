package com.example.modules.sys.userConf.service;

import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.service.CrudService;
import com.example.modules.sys.userConf.bo.UserConfBo;
import com.example.modules.sys.userConf.dto.UserConfDto;
import com.example.modules.sys.userConf.entity.SysUserConfEntity;

import java.util.Map;

/**
 * 用户个性化配置
 */
public interface UserConfService extends CrudService<UserConfBo, SysUserConfEntity, UserConfDto> {

    /**
     * 获取一个用户的所有配置， 未配置的用默认值
     *
     * @param userId 用户id
     * @return 用户的所有配置
     */
    Map<String, SysBaseConfDTO> getConf(long userId);

    boolean isSuper(String id);
}
