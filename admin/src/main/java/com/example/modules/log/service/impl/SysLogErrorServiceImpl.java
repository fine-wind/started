package com.example.modules.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.log.bo.SysLogErrorBo;
import com.example.modules.log.dao.SysLogErrorDao;
import com.example.modules.log.dto.SysLogErrorDTO;
import com.example.modules.log.entity.SysLogErrorEntity;
import com.example.modules.log.service.SysLogErrorService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public QueryWrapper<SysLogErrorEntity> getWrapper(SysLogErrorEntity params) {
        QueryWrapper<SysLogErrorEntity> wrapper = new QueryWrapper<>();
        return wrapper;
    }

    /**
     * todo xing 待修改成异步保存的
     * 提交到redis里 再用线程异步存库
     *
     * @param entity
     */
    @Override
    // @Transactional(rollbackFor = Exception.class)
    public void save(SysLogErrorEntity entity) {
        // CachedThreadPool.submitTask(() -> insert(entity));
    }

}
