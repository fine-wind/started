package com.example.started.modules.message.sms;

import com.alibaba.fastjson.JSON;
import com.example.started.modules.message.entity.SysSmsEntity;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.SpringContextUtils;
import com.example.started.modules.message.service.SysSmsService;

/**
 * 短信Factory
 *
 *
 */
public class SmsFactory {
    private static SysSmsService sysSmsService;

    static {
        SmsFactory.sysSmsService = SpringContextUtils.getBean(SysSmsService.class);
    }

    public static AbstractSmsService build(String smsCode){
        //获取短信配置信息
        SysSmsEntity smsEntity = sysSmsService.getBySmsCode(smsCode);
        SmsConfig config = JSON.parseObject(smsEntity.getSmsConfig(), SmsConfig.class);

        if(smsEntity.getPlatform() == Constant.SmsService.ALIYUN.getValue()){
//            return new AliyunSmsService(config);
        }else if(smsEntity.getPlatform() == Constant.SmsService.QCLOUD.getValue()){
//            return new QcloudSmsService(config);
        }else if(smsEntity.getPlatform() == Constant.SmsService.QINIU.getValue()){
//            return new QiniuSmsService(config);
        }

        return null;
    }
}
