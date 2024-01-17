package com.example.started.api.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.started.api.modules.message.bo.SysMailLogBo;
import com.example.started.api.modules.message.dao.SysMailLogDao;
import com.example.started.api.modules.message.dto.SysMailLogDTO;
import com.example.started.api.modules.message.entity.SysMailLogEntity;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.started.api.modules.message.service.SysMailLogService;
import org.springframework.stereotype.Service;

import static com.example.common.v0.constant.Constant.TABLE.CREATE_DATE;

@Service
public class SysMailLogServiceImpl extends BaseServiceImpl<SysMailLogBo, SysMailLogDao, SysMailLogEntity> implements SysMailLogService {

    @Override
    public PageData<SysMailLogDTO> page(SysMailLogBo params) {
        IPage<SysMailLogEntity> page = baseDao.selectPage(
                getPage(params, CREATE_DATE, false),
                getWrapper(JSON.parseObject(JSON.toJSONString(params), SysMailLogEntity.class))
        );
        return getPageData(page, SysMailLogDTO.class);
    }

    public QueryWrapper<SysMailLogEntity> getWrapper(SysMailLogEntity params) {
        Long templateId = params.getTemplateId();
        String mailTo = params.getMailTo();
        Integer status = params.getStatus();

        QueryWrapper<SysMailLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(templateId != null, "template_id", templateId);
        wrapper.like(StringUtils.isNotBlank(mailTo), "mail_to", mailTo);
        wrapper.eq(status != null, "status", status);

        return wrapper;
    }


    @Override
    public void save(Long templateId, String from, String[] to, String[] cc, String subject, String content, Integer status) {
        SysMailLogEntity log = new SysMailLogEntity();
        log.setTemplateId(templateId);
        log.setMailFrom(from);
        log.setMailTo(JSON.toJSONString(to));
        if (cc != null) {
            log.setMailCc(JSON.toJSONString(cc));
        }
        log.setSubject(subject);
        log.setContent(content);
        log.setStatus(status);
        this.insert(log);
    }

}
