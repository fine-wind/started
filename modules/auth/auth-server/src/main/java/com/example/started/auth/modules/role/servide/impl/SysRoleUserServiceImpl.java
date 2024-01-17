package com.example.started.auth.modules.role.servide.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.bo.SysRoleUserBo;
import com.example.started.auth.modules.role.dao.SysRoleUserDao;
import com.example.started.auth.modules.role.entity.SysRoleUserEntity;
import com.example.started.auth.modules.role.SysRoleUserService;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色用户关系
 *
 * @since 1.0.0
 */
@Service
@Primary
public class SysRoleUserServiceImpl extends BaseServiceImpl<SysRoleUserBo, SysRoleUserDao, SysRoleUserEntity> implements SysRoleUserService {

    @Override
    public void saveOrUpdate(String userId, List<Long> roleIdList) {
        //先删除角色用户关系
        this.deleteByUserId(userId);

        //用户没有一个角色权限的情况
        if (Objects.isNull(roleIdList) || roleIdList.isEmpty()) {
            return;
        }

        List<SysRoleUserEntity> list = new ArrayList<>(roleIdList.size());
        for (Long roleId : roleIdList) {
            SysRoleUserEntity sysRoleUserEntity = new SysRoleUserEntity();
            sysRoleUserEntity.setUserId(userId);
            sysRoleUserEntity.setRoleId(roleId);

            list.add(sysRoleUserEntity);
        }
        this.insertBatch(list);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        baseDao.deleteBatchIds(roleIds);
    }

    @Override
    public void deleteByUserId(String userIds) {
        baseDao.delete(this.getQueryWrapper(new SysRoleUserBo().setUserId(userIds)));
    }

    @Override
    public List<Long> getRoleIdList(String userId) {

        SysRoleUserBo params = new SysRoleUserBo();
        params.setUserId(userId);

        LambdaQueryWrapper<SysRoleUserEntity> queryWrapper = getQueryWrapper(params);
        queryWrapper.select(SysRoleUserEntity::getRoleId);
        List<SysRoleUserEntity> sysRoleUserEntities = baseDao.selectList(queryWrapper);
        return sysRoleUserEntities.stream().map(SysRoleUserEntity::getRoleId).distinct().collect(Collectors.toList());
    }

    @Override
    public LambdaQueryWrapper<SysRoleUserEntity> getQueryWrapper(SysRoleUserBo params) {
        LambdaQueryWrapper<SysRoleUserEntity> wrapper = super.getQueryWrapper(params);

        wrapper.eq(Objects.nonNull(params.getUserId()), SysRoleUserEntity::getUserId, params.getUserId());
        wrapper.eq(Objects.nonNull(params.getRoleId()), SysRoleUserEntity::getRoleId, params.getRoleId());

        return wrapper;
    }
}
