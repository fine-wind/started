package com.example.started.modules.message.service;

import com.example.started.modules.message.bo.SysMailTemplateBo;
import com.example.started.modules.message.dto.SysMailTemplateDTO;
import com.example.started.modules.message.entity.SysMailTemplateEntity;
import com.example.common.v0.data.service.CrudService;

import java.util.Map;

/**
 * 邮件模板
 */
public interface SysMailTemplateService extends CrudService<SysMailTemplateBo, SysMailTemplateEntity, SysMailTemplateDTO> {

    /**
     * 发送邮件
     *
     * @param id     邮件模板ID
     * @param mailTo 收件人
     * @param mailCc 抄送
     */
    boolean sendMail(Long id, String mailTo, String mailCc, Map<String, Object> map) throws Exception;
}
