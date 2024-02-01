package com.example.started.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.started.modules.sys.bo.SysRoleMenuBo;
import com.example.started.modules.sys.dao.SysRoleMenuDao;
import com.example.started.modules.sys.dto.SysRoleMenuDto;
import com.example.started.modules.sys.entity.SysRoleMenuEntity;
import com.example.started.modules.sys.service.SysRoleMenuService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 角色与菜单对应关系
 */
@Service
@Primary
public class SysRoleMenuServiceImpl extends CrudServiceImpl<SysRoleMenuBo, SysRoleMenuDao, SysRoleMenuEntity, SysRoleMenuDto> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        //先删除角色菜单关系
        List<Long> objects = new ArrayList<>(1);
        objects.add(roleId);
        deleteByRoleIds(objects);

        //角色没有一个菜单权限的情况
        if (Objects.isNull(menuIdList) || menuIdList.isEmpty()) {
            return;
        }

        //保存角色菜单关系
        List<SysRoleMenuEntity> list = new ArrayList<>(menuIdList.size());
        for (Long menuId : menuIdList) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntity.setRoleId(roleId);

            list.add(sysRoleMenuEntity);
        }
        insertBatch(list);
    }

    @Override
    public List<Long> getMenuIdList(Long roleId) {
        List<SysRoleMenuEntity> list = baseDao.selectList(this.getQueryWrapper(new SysRoleMenuBo().setRoleId(roleId)));
        return list.stream().map(SysRoleMenuEntity::getMenuId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Long> getMenuIdLists(List<Long> roleId) {
        if (Objects.isNull(roleId) || roleId.size() == 0) {
            return new ArrayList<>(0);
        }
        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = this.getQueryWrapper(new SysRoleMenuBo().setRoleIds(roleId));
        queryWrapper.select(SysRoleMenuEntity::getMenuId);
        List<SysRoleMenuEntity> list = baseDao.selectList(queryWrapper);
        return list.stream().map(SysRoleMenuEntity::getMenuId).distinct().collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(List<Long> roleIds) {
        baseDao.deleteBatchIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByMenuId(Long menuId) {
        baseDao.delete(this.getQueryWrapper(new SysRoleMenuBo().setMenuId(menuId)));
    }

    @Override
    public LambdaQueryWrapper<SysRoleMenuEntity> getQueryWrapper(SysRoleMenuBo params) {

        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = super.getQueryWrapper(params);
        queryWrapper.eq(Objects.nonNull(params.getRoleId()), SysRoleMenuEntity::getRoleId, params.getRoleId());
        queryWrapper.eq(Objects.nonNull(params.getMenuId()), SysRoleMenuEntity::getMenuId, params.getMenuId());

        queryWrapper.in(Objects.nonNull(params.getRoleIds()), SysRoleMenuEntity::getRoleId, params.getRoleIds());

        return queryWrapper;
    }
}
