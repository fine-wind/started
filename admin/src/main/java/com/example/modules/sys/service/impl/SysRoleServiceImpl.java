package com.example.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.modules.role.SysRoleUserService;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.bo.SysRoleBo;
import com.example.modules.sys.dao.SysRoleDao;
import com.example.modules.sys.dto.SysRoleDTO;
import com.example.modules.sys.entity.SysRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.modules.sys.service.*;

import java.util.List;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE_TABLE;

/**
 * 角色
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleBo, SysRoleDao, SysRoleEntity> implements SysRoleService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDataScopeService sysRoleDataScopeService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysDeptService sysDeptService;

    @Override
    public PageData<SysRoleDTO> page(SysRoleBo params) {

        IPage<SysRoleEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE_TABLE, false),
                getQueryWrapper(params)
        );

        return getPageData(page, SysRoleDTO.class);
    }

    @Override
    public List<SysRoleEntity> getList(SysRoleBo params) {
        return baseDao.selectList(getQueryWrapper(params));
    }

    @Override
    public LambdaQueryWrapper<SysRoleEntity> getQueryWrapper(SysRoleBo params) {
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = super.getQueryWrapper(params);

        String name = params.getName();

        queryWrapper.like(StringUtils.isNotBlank(name), SysRoleEntity::getName, name);

        //普通管理员，只能查询所属部门及子部门的数据
        SecurityUserDetails user = SecurityUser.getUser();
        if (user != null && !Constant.Status.SUCCESS.equals(user.getSuperAdmin())) {
            List<Long> deptIdList = sysDeptService.getSubDeptIdList(user.getDeptId());
            queryWrapper.in(deptIdList != null, SysRoleEntity::getDeptId, deptIdList);
        }


        return queryWrapper;
    }

    @Override
    public SysRoleDTO get(Long id) {
        SysRoleEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysRoleDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);

        //保存角色
        insert(entity);

        //保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());

        //保存角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDTO dto) {
        SysRoleEntity entity = ConvertUtils.sourceToTarget(dto, SysRoleEntity.class);

        //更新角色
        updateById(entity);

        //更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(entity.getId(), dto.getMenuIdList());

        //更新角色数据权限关系
        sysRoleDataScopeService.saveOrUpdate(entity.getId(), dto.getDeptIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        //删除角色
        baseDao.deleteBatchIds(ids);

        //删除角色用户关系
        sysRoleUserService.deleteByRoleIds(ids);

        //删除角色菜单关系
        sysRoleMenuService.deleteByRoleIds(ids);

        //删除角色数据权限关系
        sysRoleDataScopeService.deleteByRoleIds(ids);
    }

}
