package com.example.modules.message.service;

import com.example.common.data.service.CrudService;
import com.example.modules.message.bo.SysMailTemplateBo;
import com.example.modules.message.dto.SysMailTemplateDTO;
import com.example.modules.message.entity.SysMailTemplateEntity;

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
     * @param params 模板参数
     */
    boolean sendMail(Long id, String mailTo, String mailCc, Map<String, Object> map) throws Exception;
}
