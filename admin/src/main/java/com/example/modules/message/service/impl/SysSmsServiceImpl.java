package com.example.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.v0.data.service.impl.CrudServiceImpl;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.ConvertUtils;
import com.example.modules.message.bo.SysSmsBo;
import com.example.modules.message.dao.SysSmsDao;
import com.example.modules.message.dto.SysSmsDTO;
import com.example.modules.message.entity.SysSmsEntity;
import com.example.modules.message.service.SysSmsService;
import com.example.modules.message.sms.AbstractSmsService;
import com.example.modules.message.sms.SmsConfig;
import com.example.modules.message.sms.SmsFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class SysSmsServiceImpl extends CrudServiceImpl<SysSmsBo, SysSmsDao, SysSmsEntity, SysSmsDTO> implements SysSmsService {


    @Override
    public SysSmsDTO getById(Long id) {
        SysSmsEntity entity = baseDao.selectById(id);

        SysSmsDTO dto = ConvertUtils.sourceToTarget(entity, SysSmsDTO.class);
        dto.setConfig(JSON.parseObject(entity.getSmsConfig(), SmsConfig.class));

        return dto;
    }

    @Override
    public void send(String smsCode, String mobile, LinkedHashMap<String, String> map) {

        //短信服务
        AbstractSmsService service = SmsFactory.build(smsCode);
        if (service == null) {
            throw new ServerException(UniversalCode.SMS_CONFIG);
        }

        //发送短信
        service.sendSms(smsCode, mobile, map);
    }

    @Override
    public SysSmsEntity getBySmsCode(String smsCode) {
        QueryWrapper<SysSmsEntity> query = new QueryWrapper<>();
        query.eq("sms_code", smsCode);

        return baseDao.selectOne(query);
    }

    @Override
    public void save(SysSmsDTO dto) {
        SysSmsEntity entity = ConvertUtils.sourceToTarget(dto, SysSmsEntity.class);
        entity.setSmsConfig(JSON.toJSONString(dto.getConfig()));
        baseDao.insert(entity);
    }

    @Override
    public void update(SysSmsDTO dto) {
        SysSmsEntity entity = ConvertUtils.sourceToTarget(dto, SysSmsEntity.class);
        entity.setSmsConfig(JSON.toJSONString(dto.getConfig()));
        baseDao.updateById(entity);
    }
}
