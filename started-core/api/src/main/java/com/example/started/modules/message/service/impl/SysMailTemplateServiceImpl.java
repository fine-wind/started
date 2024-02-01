package com.example.started.modules.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.started.modules.message.bo.SysMailTemplateBo;
import com.example.started.modules.message.dao.SysMailTemplateDao;
import com.example.started.modules.message.dto.SysMailTemplateDTO;
import com.example.started.modules.message.email.EmailUtils;
import com.example.started.modules.message.entity.SysMailTemplateEntity;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.started.modules.message.service.SysMailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysMailTemplateServiceImpl
        extends CrudServiceImpl<SysMailTemplateBo, SysMailTemplateDao, SysMailTemplateEntity, SysMailTemplateDTO>
        implements SysMailTemplateService {
    @Autowired
    @Lazy
    private EmailUtils emailUtils;

    // @Override
    public QueryWrapper<SysMailTemplateEntity> getWrapper(SysMailTemplateEntity params) {
        String name = params.getName();

        QueryWrapper<SysMailTemplateEntity> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), "name", name);

        return wrapper;
    }

    @Override
    public boolean sendMail(Long id, String mailTo, String mailCc, Map<String, Object> map) throws Exception {

        String[] to = new String[]{mailTo};
        String[] cc = StringUtils.isBlank(mailCc) ? null : new String[]{mailCc};

        return emailUtils.sendMail(id, to, cc, map);
    }
}
