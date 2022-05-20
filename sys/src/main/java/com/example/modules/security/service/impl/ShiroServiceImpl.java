package com.example.modules.security.service.impl;

import com.example.modules.security.service.ShiroService;
import com.example.modules.security.user.SecurityUserDetails;
import com.example.modules.security.vo.SysMenuVo;
import com.example.modules.sys.bo.SysMenuBo;
import com.example.modules.sys.dao.SysMenuDao;
import com.example.modules.sys.dto.SysMenuDTO;
import com.example.modules.sys.service.SysMenuService;
import com.example.modules.sys.service.SysRoleDataScopeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service()
public class ShiroServiceImpl implements ShiroService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleDataScopeService sysRoleDataScopeDao;

    @Override
    public Map<String, SysMenuVo> getUserPermissions(SecurityUserDetails user) {
        //系统管理员，拥有最高权限
        List<SysMenuDTO> permissionsList = sysMenuService.getMenuList(user, new SysMenuBo());
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
                    .setUrl(item.getUrl())
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
