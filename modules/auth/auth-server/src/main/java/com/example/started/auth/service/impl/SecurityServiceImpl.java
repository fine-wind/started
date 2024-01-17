package com.example.started.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.started.auth.modules.sys.dto.SysMenuDTO;
import com.example.started.auth.modules.sys.service.SysRoleDataScopeService;
import com.example.started.auth.service.SecurityService;
import com.example.started.auth.client.user.SecurityUserDetails;
import com.example.started.auth.client.vo.SysMenuVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service()
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final SysRoleDataScopeService sysRoleDataScopeDao;


    @Override
    public Map<String, SysMenuVo> getUserPermissions(SecurityUserDetails user) {
        //系统管理员，拥有最高权限
        List<SysMenuDTO> permissionsList = new ArrayList<>(); // todo sysRoleDataScopeDao.getMenuList(user, new SysMenuBo());
        // select t3.permissions
        // from sys_role_user t1
        // left join sys_role_menu t2 on t1.role_id = t2.role_id
        // left join sys_resources t3 on t2.menu_id = t3.id
        // where t1.user_id = #{userId}
        // order by t3.sort asc

        //用户权限列表
        Map<String, SysMenuVo> map = new HashMap<>(permissionsList.size());
        for (SysMenuDTO item : permissionsList) {
            if (StringUtils.isBlank(item.getPermissions())) {
                continue;
            }
            SysMenuVo vo = new SysMenuVo();
            vo.setName(item.getName())
                    .setPath(item.getPath())
                    .setType(item.getType())
                    .setIcon(item.getIcon())
                    .setSort(item.getSort());
            for (String perm : item.getPermissions().trim().split(",")) {
                if (StringUtils.isNotBlank(perm))
                    map.put(perm, vo.setPermissions(perm));
            }
        }

        return map;
    }

    @Override
    public List<Long> getDataScopeList(Long userId) {
        return sysRoleDataScopeDao.getDataScopeList(userId);
    }
}
