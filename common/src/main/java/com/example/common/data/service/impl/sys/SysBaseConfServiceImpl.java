package com.example.common.data.service.impl.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.common.data.bo.sys.SysBaseConfBo;
import com.example.common.data.dao.sys.SysBaseConfDao;
import com.example.common.data.dto.sys.SysBaseConfDTO;
import com.example.common.data.entity.SysBaseConfEntity;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.common.data.service.sys.SysBaseConfService;
import com.example.common.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统配置基本数据
 */
@Service
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
