package com.example.modules.message.sms;

import com.example.common.constant.Constant;
import com.example.common.exception.ServerException;
import com.example.common.exception.UniversalCode;
import com.example.common.utils.SpringContextUtils;
import com.example.modules.message.service.SysSmsLogService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 腾讯云短信服务
 */
public class QcloudSmsService extends AbstractSmsService {
    public QcloudSmsService(SmsConfig config) {
        this.config = config;
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params) {
        this.sendSms(smsCode, mobile, params, config.getQcloudSignName(), config.getQcloudTemplateId());
    }

    @Override
    public void sendSms(String smsCode, String mobile, LinkedHashMap<String, String> params, String signName, String template) {
        SmsSingleSender sender = new SmsSingleSender(config.getQcloudAppId(), config.getQcloudAppKey());

        //短信参数
        ArrayList<String> paramsList = new ArrayList<>();
        if (Objects.nonNull(params)) {
            paramsList.addAll(params.values());
        }
        SmsSingleSenderResult result;
        try {
            result = sender.sendWithParam("86", mobile, Integer.parseInt(template), paramsList, signName, null, null);
        } catch (Exception e) {
            throw new ServerException(UniversalCode.SEND_SMS_ERROR, e, "");
        }

        int status = Constant.Status.SUCCESS;
        if (result.result != 0) {
            status = Constant.Status.FAIL;
        }

        //保存短信记录
        SysSmsLogService sysSmsLogService = SpringContextUtils.getBean(SysSmsLogService.class);
        sysSmsLogService.save(smsCode, Constant.SmsService.QCLOUD.getValue(), mobile, params, status);

        if (status == Constant.Status.FAIL) {
            throw new ServerException(UniversalCode.SEND_SMS_ERROR, result.errMsg);
        }
    }
}
