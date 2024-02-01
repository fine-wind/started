package com.example.started.modules.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.started.modules.log.bo.SysLogErrorBo;
import com.example.started.modules.log.dao.SysLogErrorDao;
import com.example.started.modules.log.dto.SysLogErrorDTO;
import com.example.started.modules.log.entity.SysLogErrorEntity;
import com.example.started.modules.log.service.SysLogErrorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

/**
 * 异常日志
 *
 * @since 1.0.0
 */
@Service
public class SysLogErrorServiceImpl extends BaseServiceImpl<SysLogErrorBo, SysLogErrorDao, SysLogErrorEntity> implements SysLogErrorService {

    @Override
    public PageData<SysLogErrorDTO> page(SysLogErrorBo params) {
        IPage<SysLogErrorEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getWrapper(JSON.parseObject(JSON.toJSONString(params), SysLogErrorEntity.class))
        );

        return getPageData(page, SysLogErrorDTO.class);
    }

    @Override
    public List<SysLogErrorDTO> list(SysLogErrorBo params) {
        List<SysLogErrorEntity> entityList = baseDao.selectList(getWrapper(JSON.parseObject(JSON.toJSONString(params), SysLogErrorEntity.class)));

        return ConvertUtils.sourceToTarget(entityList, SysLogErrorDTO.class);
    }

    public LambdaQueryWrapper<SysLogErrorEntity> getWrapper(SysLogErrorEntity params) {
        LambdaQueryWrapper<SysLogErrorEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Objects.nonNull(params.getIp()), SysLogErrorEntity::getIp, params.getIp());
        return wrapper;
    }

    /**
     * todo xing 待修改成异步保存的
     * 提交到redis里 再用线程异步存库
     *
     * @param entity log
     */
    @Override
    public void save(SysLogErrorEntity entity) {
        new Thread(() -> insert(entity)).start();
    }

}
