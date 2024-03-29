package com.example.started.modules.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.modules.log.bo.SysLogOperationBo;
import com.example.started.modules.log.dao.SysLogOperationDao;
import com.example.started.modules.log.dto.SysLogOperationDTO;
import com.example.started.modules.log.entity.SysLogOperationEntity;
import com.example.started.modules.log.service.SysLogOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Service
public class SysLogOperationServiceImpl extends BaseServiceImpl<SysLogOperationBo, SysLogOperationDao, SysLogOperationEntity> implements SysLogOperationService {

    @Override
    public PageData<SysLogOperationDTO> page(SysLogOperationBo params) {
        IPage<SysLogOperationEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getWrapper(params)
        );

        return getPageData(page, SysLogOperationDTO.class);
    }

    @Override
    public List<SysLogOperationDTO> list(SysLogOperationBo params) {
        List<SysLogOperationEntity> entityList = baseDao.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysLogOperationDTO.class);
    }

    public LambdaQueryWrapper<SysLogOperationEntity> getWrapper(SysLogOperationBo params) {
        return super.getQueryWrapper(params)
                .eq(Objects.nonNull(params.getStatus()), SysLogOperationEntity::getStatus, params.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysLogOperationEntity entity) {
        insert(entity);
    }

}
