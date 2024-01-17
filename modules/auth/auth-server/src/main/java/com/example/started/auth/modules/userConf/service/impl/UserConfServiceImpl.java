package com.example.started.auth.modules.userConf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.common.v0.enums.UserConfigBaseEnum;
import com.example.started.auth.modules.userConf.bo.UserConfBo;
import com.example.started.auth.modules.userConf.dto.UserConfDto;
import com.example.started.auth.modules.userConf.entity.SysUserConfEntity;
import com.example.started.auth.modules.userConf.dao.UserConfDao;
import com.example.started.auth.modules.userConf.service.SysBaseConfService;
import com.example.started.auth.modules.userConf.service.UserConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户个性化配置
 */
@Service
public class UserConfServiceImpl extends CrudServiceImpl<UserConfBo, UserConfDao, SysUserConfEntity, UserConfDto> implements UserConfService {


    private final SysBaseConfService baseConfService;

    @Autowired
    public UserConfServiceImpl(SysBaseConfService baseConfService) {
        this.baseConfService = baseConfService;
    }

    @Override
    public LambdaQueryWrapper<SysUserConfEntity> getQueryWrapper(UserConfBo params) {
        LambdaQueryWrapper<SysUserConfEntity> queryWrapper = super.getQueryWrapper(params);

        queryWrapper.eq(Objects.nonNull(params.getUserId()), SysUserConfEntity::getUserId, params.getUserId());
        queryWrapper.eq(Objects.nonNull(params.getItem()), SysUserConfEntity::getK, params.getItem());

        return queryWrapper;
    }


    @Override
    public Map<String, SysBaseConfDTO> getConf(long userId) {

        List<UserConfDto> list = list(new UserConfBo().setUserId(userId));

        List<SysBaseConfDTO> all = baseConfService.getAll();

        Map<String, SysBaseConfDTO> map = new HashMap<>(all.size());

        all.forEach((t) -> map.put(t.getItem(), t));
        list.forEach((t) -> {
            SysBaseConfDTO sysBaseConfDTO = map.get(t.getItem());
            if (Objects.nonNull(sysBaseConfDTO)) {
                sysBaseConfDTO.setContent(t.getContent());
            }
        });

        return map;
    }

    @Override
    public boolean isSuper(String id) {
        LambdaQueryWrapper<SysUserConfEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserConfEntity::getK, UserConfigBaseEnum.SUPERMAN.name());
        return baseDao.selectCount(queryWrapper) > 0;
    }
}
