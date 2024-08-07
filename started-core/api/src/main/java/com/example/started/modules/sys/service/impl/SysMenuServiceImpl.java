package com.example.started.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.started.auth.Se;
import com.example.started.modules.sys.bo.SysMenuBo;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.*;
import com.example.started.modules.sys.dao.SysMenuDao;
import com.example.started.modules.sys.dto.SysMenuDTO;
import com.example.started.modules.sys.entity.SysResourcesEntity;
import com.example.started.modules.sys.service.SysMenuService;
import com.example.started.modules.sys.service.SysRoleMenuService;
import com.example.started.auth.role.user.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Primary
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuBo, SysMenuDao, SysResourcesEntity> implements SysMenuService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public SysMenuDTO get(Long id) {

        SysResourcesEntity entity = baseDao.selectById(id);
        if (Objects.nonNull(entity.getPid())) {
            SysResourcesEntity menuEntity = baseDao.selectById(entity.getPid());
            if (Objects.nonNull(menuEntity)) {
                entity.setParentName(menuEntity.getName());
            }
        }
        return ConvertUtils.sourceToTarget(entity, SysMenuDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysMenuDTO dto) {
        SysResourcesEntity entity = ConvertUtils.sourceToTarget(dto, SysResourcesEntity.class);

        //保存菜单
        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuDTO dto) {
        SysResourcesEntity entity = ConvertUtils.sourceToTarget(dto, SysResourcesEntity.class);

        //上级菜单不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException(UniversalCode.SUPERIOR_MENU_ERROR);
        }

        //更新菜单
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //删除菜单
        deleteById(id);

        //删除角色菜单关系
        sysRoleMenuService.deleteByMenuId(id);
    }

    @Override
    public List<SysMenuDTO> getAllMenuTree(Integer type) {
        List<SysMenuDTO> menuList = this.getMenuList(new SysMenuBo().setType(type));
        return TreeUtils.build(menuList);
    }


    @Override
    public List<SysMenuDTO> getMenuList(SysMenuBo bo) {
        List<SysResourcesEntity> menuList;
        //系统管理员，拥有最高权限
        if (Se.SUPER_USER()) {
            menuList = baseDao.selectList(getQueryWrapper(bo));
        } else {
            menuList = baseDao.getUserMenuList(Se.getUserId().toString(), bo.getType(), HttpContextUtils.getLanguage());
        }

        return ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
    }

    @Override
    public List<SysMenuDTO> getUserMenuTree(Integer type) {

        SysMenuBo sysMenuBo = new SysMenuBo().setType(type);
        List<SysMenuDTO> dtoList = this.getMenuList(sysMenuBo);

        dtoList = dtoList.stream().filter(e -> Constant.Status.FAIL.equals(e.getSubpage())).collect(Collectors.toList());
        return TreeUtils.build(dtoList);
    }

    @Override
    public List<SysMenuDTO> getListPid(Long pid) {
        List<SysResourcesEntity> menuList = baseDao.selectList(this.getQueryWrapper(new SysMenuBo()).eq(SysResourcesEntity::getPid, pid));

        return ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
    }

    @Override
    public Set<String> getListByIds(List<Long> ids) {

        if (Objects.nonNull(ids) && ids.isEmpty()) {
            ids.add(Constant.Status.FAIL.longValue());
        }
        SysMenuBo params = new SysMenuBo();
        params.setIds(ids);

        LambdaQueryWrapper<SysResourcesEntity> queryWrapper = this.getQueryWrapper(params);
        queryWrapper.isNotNull(SysResourcesEntity::getPermissions);
        queryWrapper.select(SysResourcesEntity::getPermissions);

        List<SysResourcesEntity> menuList = baseDao.selectList(queryWrapper);
        return menuList.stream()
                .map(SysResourcesEntity::getPermissions)
                .distinct()
                .filter(StringUtil::noAllEmpty)
                .collect(Collectors.toSet());
    }


    @Override
    public LambdaQueryWrapper<SysResourcesEntity> getQueryWrapper(SysMenuBo params) {
        return super.getQueryWrapper(params)
                .in(Objects.nonNull(params.getIds()), SysResourcesEntity::getId, params.getIds())
                .eq(Objects.nonNull(params.getPid()), SysResourcesEntity::getPid, params.getPid())
                .eq(Objects.nonNull(params.getType()), SysResourcesEntity::getType, params.getType())
                .eq(Objects.nonNull(params.getSubpage()), SysResourcesEntity::getSubpage, params.getSubpage())
                .orderByAsc(SysResourcesEntity::getSort);
    }

    private void setlr(SysMenuDTO dto) {
        SysResourcesEntity entity = new SysResourcesEntity();
        entity.setId(dto.getId());
        entity.setL(dto.getL());
        entity.setR(dto.getR());
        baseDao.updateById(entity);
    }

    @Override
    public void run(String... args) throws Exception {
        LambdaQueryWrapper<SysResourcesEntity> objectLambdaQueryWrapper = new LambdaQueryWrapper<SysResourcesEntity>().orderByAsc(SysResourcesEntity::getSort);
        List<SysResourcesEntity> menuList = baseDao.selectList(objectLambdaQueryWrapper);
        List<SysMenuDTO> list = ConvertUtils.sourceToTarget(menuList, SysMenuDTO.class);
        List<SysMenuDTO> build = TreeUtils.build(list);
        TreeUtils.setLr(build, new AtomicInteger(0), this::setlr);
    }
}
