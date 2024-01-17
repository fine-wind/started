package com.example.started.auth.modules.userConf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.bo.sys.SysBaseConfBo;
import com.example.started.auth.modules.userConf.dao.SysBaseConfDao;
import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.entity.SysConfBaseEntity;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.common.v0.utils.StringUtil;
import com.example.common.v0.enums.UserConfigBaseEnum;
import com.example.started.auth.modules.userConf.service.SysBaseConfService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统配置基本数据
 */
@Log4j2
@Service
@Primary
public class SysBaseConfServiceImpl extends CrudServiceImpl<SysBaseConfBo, SysBaseConfDao, SysConfBaseEntity, SysBaseConfDTO> implements SysBaseConfService {

    @Override
    public LambdaQueryWrapper<SysConfBaseEntity> getQueryWrapper(SysBaseConfBo params) {
        return super.getQueryWrapper(params)
                .eq(StringUtil.isNotEmpty(params.getItem()), SysConfBaseEntity::getCode, params.getItem())
                .like(params.getTitle() != null, SysConfBaseEntity::getName, params.getTitle())
                ;
    }


    @Override
    public List<SysBaseConfDTO> getAll() {
        return list(new SysBaseConfBo());
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.trace("加载用户基本配置");
        List<SysConfBaseEntity> list = baseDao.selectList(new LambdaQueryWrapper<>());
        Map<String, SysConfBaseEntity> map = new HashMap<>(list.size());
        list.forEach(e -> map.put(e.getCode(), e));
        for (UserConfigBaseEnum value : UserConfigBaseEnum.values()) {
            if (Objects.isNull(map.get(value.name()))) {
                baseDao.insert(new SysConfBaseEntity().setCode(value.name()).setName(value.getName()));
            }
        }
    }
}
