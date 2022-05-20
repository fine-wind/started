package com.example.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.common.exception.ServerException;
import com.example.common.exception.UniversalCode;
import com.example.modules.message.bo.SysMailTemplateBo;
import com.example.modules.message.dao.SysMailTemplateDao;
import com.example.modules.message.dto.SysMailTemplateDTO;
import com.example.modules.message.email.EmailUtils;
import com.example.modules.message.entity.SysMailTemplateEntity;
import com.example.modules.message.service.SysMailTemplateService;
import org.apache.commons.lang3.StringUtils;
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
    public boolean sendMail(Long id, String mailTo, String mailCc, String params) throws Exception {
        Map<String, Object> map = null;
        try {
            if (StringUtils.isNotEmpty(params)) {
                map = JSON.parseObject(params, Map.class);
            }
        } catch (Exception e) {
            throw new ServerException(UniversalCode.JSON_FORMAT_ERROR);
        }
        String[] to = new String[]{mailTo};
        String[] cc = StringUtils.isBlank(mailCc) ? null : new String[]{mailCc};

        return emailUtils.sendMail(id, to, cc, map);
    }
}
