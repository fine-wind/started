package com.example.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.bo.SysDeptBo;
import com.example.common.v0.data.dao.SysDeptDao;
import com.example.common.v0.data.entity.SysGroupEntity;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.TreeUtils;
import com.example.modules.security.user.SecurityUser;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.sys.dept.dto.SysDeptDTO;
import com.example.modules.sys.service.SysDeptService;
import com.example.modules.sys.user.v1.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptBo, SysDeptDao, SysGroupEntity> implements SysDeptService {
    @Autowired
    private UserService sysUserService;

    @Override
    public List<SysDeptDTO> list(SysDeptBo bo) {
        //普通管理员，只能查询所属部门及子部门的数据
        SecurityUserDetails user = SecurityUser.getUser();

        if (Objects.nonNull(user.getDeptId())) {
            bo.setDeptIdList(this.getSubDeptIdList(user.getDeptId()));
        }
        if (Constant.Status.SUCCESS.equals(user.getSuperAdmin())) {
            bo.setPid(null);
            bo.setDeptIdList(null);
        }
        List<SysGroupEntity> entityList = baseDao.selectList(this.getQueryWrapper(bo));

        List<SysDeptDTO> dtoList = ConvertUtils.sourceToTarget(entityList, SysDeptDTO.class);

        return TreeUtils.build(dtoList);
    }

    @Override
    public SysDeptDTO get(Long id) {
        //超级管理员，部门ID为null
        if (id == null) {
            return null;
        }

        SysGroupEntity entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, SysDeptDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysDeptDTO dto) {
        SysGroupEntity entity = ConvertUtils.sourceToTarget(dto, SysGroupEntity.class);

        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysDeptDTO dto) {
        SysGroupEntity entity = ConvertUtils.sourceToTarget(dto, SysGroupEntity.class);

        //上级部门不能为自身
        if (entity.getId().equals(entity.getPid())) {
            throw new ServerException(UniversalCode.SUPERIOR_DEPT_ERROR, "上级部门不能为自身");
        }

        //上级部门不能为下级部门
        List<Long> subDeptList = getSubDeptIdList(entity.getId());
        if (subDeptList.contains(entity.getPid())) {
            throw new ServerException(UniversalCode.SUPERIOR_DEPT_ERROR, "上级部门不能为下级部门");
        }

        // entity.setPids(getPidList(entity.getPid()));
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        //判断是否有子部门
        List<Long> subList = getSubDeptIdList(id);
        if (subList.size() > 0) {
            throw new ServerException(Constant.UniversalCode.UNPROCESSABLE_ENTITY, "此部门下还有子部门！");
        }

        //判断部门下面是否有用户
        Long count = sysUserService.getCountByDeptId(id);
        if (count > 0) {
            throw new ServerException(Constant.UniversalCode.UNPROCESSABLE_ENTITY, "此部门下还有用户！");
        }

        //删除
        baseDao.deleteById(id);
    }

    @Override
    public List<Long> getSubDeptIdList(Long id) {

        List<SysGroupEntity> deptList = baseDao.selectList(this.getQueryWrapper(new SysDeptBo().setPid(id)));

        return deptList.stream().map(SysGroupEntity::getId).distinct().collect(Collectors.toList());
    }

    /**
     * 获取所有上级部门ID
     *
     * @param pid 上级ID
     */
    private String getPidList(Long pid) {
        //顶级部门，无上级部门
        if (Constant.ROOT.equals(pid)) {
            return Constant.Status.UNKNOWN + "";
        }

        //所有部门的id、pid列表
        List<SysGroupEntity> deptList = baseDao.selectList(null);

        //list转map
        Map<Long, SysGroupEntity> map = new HashMap<>(deptList.size());
        for (SysGroupEntity entity : deptList) {
            map.put(entity.getId(), entity);
        }

        //递归查询所有上级部门ID列表
        List<Long> pidList = new ArrayList<>();
        getPidTree(pid, map, pidList);

        return StringUtils.join(pidList, ",");
    }

    private void getPidTree(Long pid, Map<Long, SysGroupEntity> map, List<Long> pidList) {
        //顶级部门，无上级部门
        if (Constant.ROOT.equals(pid) || Objects.isNull(pid)) {
            return;
        }

        //上级部门存在
        SysGroupEntity parent = map.get(pid);
        if (parent != null) {
            getPidTree(parent.getPid(), map, pidList);
        }

        pidList.add(pid);
    }

    @Override
    public LambdaQueryWrapper<SysGroupEntity> getQueryWrapper(SysDeptBo params) {
        LambdaQueryWrapper<SysGroupEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getPid()), SysGroupEntity::getPid, params.getPid());

        // boolean pids = Objects.nonNull(params.getPids()) && !String.valueOf(Constant.Status.UNKNOWN).equals(params.getPids());
        // queryWrapper.eq(pids, SysDeptEntity::getPids, params.getPids());

        boolean deptIdList = Objects.nonNull(params.getDeptIdList()) && params.getDeptIdList().size() > 0;
        queryWrapper.in(deptIdList, SysGroupEntity::getId, params.getDeptIdList());

        return queryWrapper;
    }
}
