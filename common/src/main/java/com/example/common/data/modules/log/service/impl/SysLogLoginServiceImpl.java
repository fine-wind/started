package com.example.common.data.modules.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.constant.Constant;
import com.example.common.data.modules.log.bo.SysLogLoginBo;
import com.example.common.data.modules.log.dao.SysLogLoginDao;
import com.example.common.data.modules.log.dto.SysLogLoginDTO;
import com.example.common.data.page.PageData;
import com.example.common.data.service.impl.BaseServiceImpl;
import com.example.common.utils.ConvertUtils;
import com.example.common.utils.HttpContextUtils;
import com.example.common.utils.IpUtils;
import com.example.common.data.modules.log.entity.SysLogLoginEntity;
import com.example.common.data.modules.log.service.SysLogLoginService;
import com.example.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.common.constant.Constant.TABLE.*;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Service
public class SysLogLoginServiceImpl extends BaseServiceImpl<SysLogLoginBo, SysLogLoginDao, SysLogLoginEntity> implements SysLogLoginService {

    @Override
    public void save(SysLogLoginEntity entity) {
        baseDao.insert(entity);
    }

    @Override
    public PageData<SysLogLoginDTO> page(SysLogLoginBo params) {
        IPage<SysLogLoginEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
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
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setIp(IpUtils.getIpAddr(request));
        log.setStatus(0);
        log.setCreator(Constant.Status.UNKNOWN);
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
