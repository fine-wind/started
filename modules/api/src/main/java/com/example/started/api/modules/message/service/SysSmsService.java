package com.example.started.api.modules.message.service;

import com.example.started.api.modules.message.bo.SysSmsBo;
import com.example.started.api.modules.message.dto.SysSmsDTO;
import com.example.started.api.modules.message.entity.SysSmsEntity;
import com.example.common.v0.data.service.CrudService;

import java.util.LinkedHashMap;

/**
 * 短信
 */
public interface SysSmsService extends CrudService<SysSmsBo, SysSmsEntity, SysSmsDTO> {

    /**
     * 发送短信
     *
     * @param smsCode 短信编码
     * @param mobile  手机号
     * @param map  短信参数
     */
    void send(String smsCode, String mobile, LinkedHashMap<String, String> map);

    SysSmsEntity getBySmsCode(String smsCode);

}

