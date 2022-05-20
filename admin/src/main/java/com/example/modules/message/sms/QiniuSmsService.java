package com.example.modules.message.sms;

import com.example.common.constant.Constant;
import com.example.common.exception.ServerException;
import com.example.common.exception.UniversalCode;
import com.example.common.utils.SpringContextUtils;
import com.example.modules.message.service.SysSmsLogService;
import com.qiniu.http.Response;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;

import java.util.LinkedHashMap;

/**
 * 七牛短信服务
 *
 *
 */
public class QiniuSmsService extends AbstractSmsService {
    private SmsManager smsManager;

    public QiniuSmsService(SmsConfig config){
        this.config = config;

        //初始化
        init();
    }


    private void init(){
        Auth auth = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey());
        smsManager = new SmsManager(auth);
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(smsCode, mobile, params, null, config.getQiniuTemplateId());
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params, String signName, String template) {
        Response response;
        try {
            response = smsManager.sendSingleMessage(template, mobile, params);
        } catch (Exception e) {
            throw new ServerException(UniversalCode.SEND_SMS_ERROR, e, "");
        }

        int status = Constant.Status.SUCCESS;
        if(!response.isOK()){
            status = Constant.Status.FAIL;
        }

        //保存短信记录
        SysSmsLogService sysSmsLogService = SpringContextUtils.getBean(SysSmsLogService.class);
        sysSmsLogService.save(smsCode, Constant.SmsService.QCLOUD.getValue(), mobile, params, status);

        if(status == Constant.Status.FAIL){
            throw new ServerException(UniversalCode.SEND_SMS_ERROR, response.error);
        }
    }
}
