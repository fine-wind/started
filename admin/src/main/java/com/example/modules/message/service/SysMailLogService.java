package com.example.modules.message.service;

import com.example.common.data.page.PageData;
import com.example.common.data.service.BaseService;
import com.example.modules.message.bo.SysMailLogBo;
import com.example.modules.message.dto.SysMailLogDTO;
import com.example.modules.message.entity.SysMailLogEntity;

/**
 * 邮件发送记录
 */
public interface SysMailLogService extends BaseService<SysMailLogBo, SysMailLogEntity> {

    PageData<SysMailLogDTO> page(SysMailLogBo params);

    /**
     * 保存邮件发送记录
     *
     * @param templateId 模板ID
     * @param from       发送者
     * @param to         收件人
     * @param cc         抄送
     * @param subject    主题
     * @param content    邮件正文
     * @param status     状态
     */
    void save(Long templateId, String from, String[] to, String[] cc, String subject, String content, Integer status);
}

