package com.example.modules.master.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.modules.master.bo.SuperUserBo;
import com.example.modules.master.dao.MasterUserDao;
import com.example.modules.master.dto.SuperUserDto;
import com.example.modules.master.entity.SysSuperUserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MasterUserServiceImpl extends CrudServiceImpl<SuperUserBo, MasterUserDao, SysSuperUserEntity, SuperUserDto> implements SuperUserService {

    @Override
    public boolean isSuper(Long userId) {
        SuperUserBo user = new SuperUserBo();
        user.setUserId(userId);
        List<SysSuperUserEntity> sysMasterUserEntities = baseDao.selectList(this.getQueryWrapper(user));
        //  for (SysMasterUserEntity item : sysMasterUserEntities) {
        //
        //  }
        return sysMasterUserEntities.size() > 0;
    }

    @Override
    public LambdaQueryWrapper<SysSuperUserEntity> getQueryWrapper(SuperUserBo params) {
        LambdaQueryWrapper<SysSuperUserEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getUserId()), SysSuperUserEntity::getUserId, params.getUserId());
        return queryWrapper;
    }
}
