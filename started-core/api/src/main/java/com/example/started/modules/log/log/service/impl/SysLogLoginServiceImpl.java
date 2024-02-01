package com.example.started.modules.log.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.started.modules.log.log.bo.SysLogLoginBo;
import com.example.started.modules.log.log.dto.SysLogLoginDTO;
import com.example.started.modules.log.log.entity.SysLogLoginEntity;
import com.example.started.modules.log.log.service.SysLogLoginService;
import com.example.common.v0.constant.Constant;
import com.example.started.modules.log.log.dao.SysLogLoginDao;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.ConvertUtils;
import com.example.common.v0.utils.HttpContextUtils;
import com.example.common.v0.utils.StringUtil;
import com.example.common.v1.base.SelectPageUtil;
import com.example.common.v1.base.service.impl.BaseServiceV1Impl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Service
@Primary
public class SysLogLoginServiceImpl extends BaseServiceV1Impl<SysLogLoginBo, SysLogLoginDao, SysLogLoginEntity> implements SysLogLoginService {

    @Override
    public void save(SysLogLoginEntity entity) {
        baseDao.insert(entity);
    }

    @Override
    public PageData<SysLogLoginDTO> page(SysLogLoginBo params) {
        IPage<SysLogLoginEntity> page = baseDao.selectPage(
                SelectPageUtil.getPage(params, null, CREATE_DATE, false),
                getQueryWrapper(params)
        );

        return getPageData(page, SysLogLoginDTO.class);
    }

    @Override
    public List<SysLogLoginDTO> list(SysLogLoginBo params) {
        List<SysLogLoginEntity> entityList = baseDao.selectList(getQueryWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, SysLogLoginDTO.class);
    }

    public QueryWrapper<SysLogLoginEntity> getWrapper(Map<String, Object> params) {
        String status = (String) params.get("status");
        String creatorName = (String) params.get("creatorName");

        QueryWrapper<SysLogLoginEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(status), "status", status);
        wrapper.like(StringUtils.isNotBlank(creatorName), "creator_name", creatorName);

        return wrapper;
    }

    @Override
    public Long getVisitCount() {
        return baseDao.selectCount(new LambdaQueryWrapper<SysLogLoginEntity>().eq(SysLogLoginEntity::getOperation, 2));
    }

    @Override
    public SysLogLoginEntity getEntity() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //用户信息
        SysLogLoginEntity log = new SysLogLoginEntity();
        log.setOperation(0);//0:login,1:logout,2 mainPage visited
//        log.setIp(IpUtils.getIpAddr(request));
        // todo log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setStatus(0);
        log.setCreator(String.valueOf(Constant.Status.UNKNOWN));
        log.setCreateDate(new Date());

        return log;
    }

    @Override
    public LambdaQueryWrapper<SysLogLoginEntity> getQueryWrapper(SysLogLoginBo params) {
        return super.getQueryWrapper(params)
                .eq(Objects.nonNull(params.getStatus()), SysLogLoginEntity::getStatus, params.getStatus())
                .like(StringUtil.isNotEmpty(params.getCreatorName()), SysLogLoginEntity::getCreatorName, params.getCreatorName())
                ;
    }
}
