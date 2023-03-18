package com.example.common.v0.data.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.v0.data.bo.sys.SysBaseConfBo;
import com.example.common.v0.data.dao.sys.SysBaseConfDao;
import com.example.common.v0.data.dto.sys.SysBaseConfDTO;
import com.example.common.v0.data.entity.SysBaseConfEntity;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.common.v0.data.service.sys.SysBaseConfService;
import com.example.common.v0.utils.StringUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置基本数据
 */
@Service
@Primary
public class SysBaseConfServiceImpl extends CrudServiceImpl<SysBaseConfBo, SysBaseConfDao, SysBaseConfEntity, SysBaseConfDTO> implements SysBaseConfService {

    @Override
    public LambdaQueryWrapper<SysBaseConfEntity> getQueryWrapper(SysBaseConfBo params) {
        return super.getQueryWrapper(params)
                .eq(StringUtil.isNotEmpty(params.getItem()), SysBaseConfEntity::getItem, params.getItem())
                .like(params.getTitle() != null, SysBaseConfEntity::getTitle, params.getTitle())
                ;
    }


    @Override
    public List<SysBaseConfDTO> getAll() {
        return list(new SysBaseConfBo());
    }
}
