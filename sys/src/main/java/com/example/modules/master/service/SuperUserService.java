package com.example.modules.master.service;

import com.example.common.data.service.CrudService;
import com.example.modules.master.bo.SuperUserBo;
import com.example.modules.master.dto.SuperUserDto;
import com.example.modules.master.entity.SysSuperUserEntity;

/**
 * 用户
 */
public interface SuperUserService extends CrudService<SuperUserBo, SysSuperUserEntity, SuperUserDto> {


    /**
     * 是否是超级用户
     *
     * @param userId 用户id
     * @return yes or no
     */
    boolean isSuper(Long userId);
}
