package com.example.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.modules.sys.bo.SysRoleDataScopeBo;
import com.example.modules.sys.dao.SysRoleDataScopeDao;
import com.example.modules.sys.dto.SysRoleDataScopeDto;
import com.example.modules.sys.entity.SysRoleDataScopeEntity;
import com.example.modules.sys.service.SysRoleDataScopeService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色数据权限
 *
 * @since 1.0.0
 */
@Service
@Primary
public class SysRoleDataScopeServiceImpl extends CrudServiceImpl<SysRoleDataScopeBo, SysRoleDataScopeDao, SysRoleDataScopeEntity, SysRoleDataScopeDto> implements SysRoleDataScopeService {

    @Override
    public List<Long> getDeptIdList(Long roleId) {
        List<SysRoleDataScopeEntity> list = baseDao.selectList(this.getQueryWrapper(new SysRoleDataScopeBo().setRoleId(roleId)));
        return list.stream().map(SysRoleDataScopeEntity::getDeptId).distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色数据权限关系
        List<Long> roleIds = new ArrayList<>(1);
        roleIds.add(roleId);
        deleteByRoleIds(roleIds);

        //角色没有一个数据权限的情况
        if (Objects.isNull(deptIdList) || deptIdList.isEmpty()) {
            return;
        }

        //保存角色数据权限关系
        List<SysRoleDataScopeEntity> list = new ArrayList<>(deptIdList.size());
        for (Long deptId : deptIdList) {
            SysRoleDataScopeEntity sysRoleDataScopeEntity = new SysRoleDataScopeEntity();
            sysRoleDataScopeEntity.setDeptId(deptId);
            sysRoleDataScopeEntity.setRoleId(roleId);
            list.add(sysRoleDataScopeEntity);
        }
        insertBatch(list);
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        baseDao.delete(this.getQueryWrapper(new SysRoleDataScopeBo().setRoleIds(roleIds)));
    }

    /**
     * todo
     *
     * @param userId .
     * @return .
     */
    @Override
    public List<Long> getDataScopeList(Long userId) {
        // List<Long> list = baseDao.getDataScopeList(userId);
        return null;
    }

    @Override
    public LambdaQueryWrapper<SysRoleDataScopeEntity> getQueryWrapper(SysRoleDataScopeBo params) {
        LambdaQueryWrapper<SysRoleDataScopeEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getRoleId()), SysRoleDataScopeEntity::getRoleId, params.getRoleId());
        queryWrapper.eq(Objects.nonNull(params.getDeptId()), SysRoleDataScopeEntity::getDeptId, params.getDeptId());

        queryWrapper.in(Objects.nonNull(params.getRoleIds()), SysRoleDataScopeEntity::getRoleId, params.getRoleIds());

        return queryWrapper;
    }
}
