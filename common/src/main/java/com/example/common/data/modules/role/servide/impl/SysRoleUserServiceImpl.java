package com.example.common.data.modules.role.servide.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.data.modules.role.dao.SysRoleUserDao;
import com.example.common.data.service.impl.BaseServiceImpl;
import com.example.common.data.bo.SysRoleUserBo;
import com.example.common.data.modules.role.entity.SysRoleUserEntity;
import com.example.common.data.modules.role.SysRoleUserService;
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
public class SysRoleUserServiceImpl extends BaseServiceImpl<SysRoleUserBo, SysRoleUserDao, SysRoleUserEntity> implements SysRoleUserService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除角色用户关系
        deleteByUserId(userId);

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
    public void deleteByUserId(Long userIds) {
        baseDao.delete(this.getQueryWrapper(new SysRoleUserBo().setUserId(userIds)));
    }

    @Override
    public List<Long> getRoleIdList(Long userId) {

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
