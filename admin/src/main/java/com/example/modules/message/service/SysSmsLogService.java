package com.example.modules.message.service;

import com.example.common.v0.data.service.CrudService;
import com.example.modules.message.bo.SysSmsLogBo;
import com.example.modules.message.dto.SysSmsLogDTO;
import com.example.modules.message.entity.SysSmsLogEntity;

import java.util.LinkedHashMap;

/**
 * 短信日志
 */
public interface SysSmsLogService extends CrudService<SysSmsLogBo, SysSmsLogEntity, SysSmsLogDTO> {

    /**
     * 保存短信发送记录
     *
     * @param smsCode  短信编码
     * @param platform 平台
     * @param mobile   手机号
     * @param params   短信参数
     * @param status   发送状态
     */
    void save(String smsCode, Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status);
}
