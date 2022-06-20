package com.example.modules.message.service;

import com.example.common.data.service.CrudService;
import com.example.modules.message.bo.SysSmsBo;
import com.example.modules.message.dto.SysSmsDTO;
import com.example.modules.message.entity.SysSmsEntity;

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

