package com.example.modules.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.data.service.impl.CrudServiceImpl;
import com.example.modules.message.bo.SysSmsLogBo;
import com.example.modules.message.dao.SysSmsLogDao;
import com.example.modules.message.dto.SysSmsLogDTO;
import com.example.modules.message.entity.SysSmsLogEntity;
import com.example.modules.message.service.SysSmsLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * 短信日志
 */
@Service
public class SysSmsLogServiceImpl extends CrudServiceImpl<SysSmsLogBo, SysSmsLogDao, SysSmsLogEntity, SysSmsLogDTO> implements SysSmsLogService {

    // @Override
    public QueryWrapper<SysSmsLogEntity> getWrapper(SysSmsLogEntity params) {
        String smsCode = params.getSmsCode();
        String mobile = params.getMobile();
        Integer status = params.getStatus();

        QueryWrapper<SysSmsLogEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(smsCode), "sms_code", smsCode);
        wrapper.like(StringUtils.isNotBlank(mobile), "mobile", mobile);
        wrapper.eq(status != null, "status", status);

        return wrapper;
    }


    @Override
    public void save(String smsCode, Integer platform, String mobile, LinkedHashMap<String, String> params, Integer status) {

        SysSmsLogEntity smsLog = new SysSmsLogEntity();
        smsLog.setSmsCode(smsCode);
        smsLog.setPlatform(platform);
        smsLog.setMobile(mobile);

        //设置短信参数
        if (Objects.nonNull(params)) {
            int index = 1;
            for (String value : params.values()) {
                if (index == 1) {
                    smsLog.setParams1(value);
                } else if (index == 2) {
                    smsLog.setParams2(value);
                } else if (index == 3) {
                    smsLog.setParams3(value);
                } else if (index == 4) {
                    smsLog.setParams4(value);
                }
                index++;
            }
        }

        smsLog.setStatus(status);

        baseDao.insert(smsLog);
    }
}
