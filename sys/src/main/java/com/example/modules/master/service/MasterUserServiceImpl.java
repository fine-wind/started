package com.example.modules.master.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.constant.Constant;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.modules.master.bo.MasterUserBo;
import com.example.modules.master.dao.MasterUserDao;
import com.example.modules.master.dto.MasterUserDto;
import com.example.modules.master.entity.SysMasterUserEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MasterUserServiceImpl extends CrudServiceImpl<MasterUserBo, MasterUserDao, SysMasterUserEntity, MasterUserDto> implements MasterUserService {

    @Override
    public boolean isMaster(Long userId) {
        MasterUserBo user = new MasterUserBo();
        user.setUserId(userId);
        List<SysMasterUserEntity> sysMasterUserEntities = baseDao.selectList(this.getQueryWrapper(user));
        //  for (SysMasterUserEntity item : sysMasterUserEntities) {
        //
        //  }
        return sysMasterUserEntities.size() > 0;
    }

    @Override
    public LambdaQueryWrapper<SysMasterUserEntity> getQueryWrapper(MasterUserBo params) {
        LambdaQueryWrapper<SysMasterUserEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getUserId()), SysMasterUserEntity::getUserId, params.getUserId());
        return queryWrapper;
    }
}
