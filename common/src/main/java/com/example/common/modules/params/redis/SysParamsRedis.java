package com.example.common.modules.params.redis;

import com.example.cache.constant.CacheCommonKeys;
import com.example.common.constant.Constant;
import org.springframework.stereotype.Component;

import static com.example.common.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
@Component
public class SysParamsRedis {


    public void delete(String[] paramCodes) {
        for (String paramCode : paramCodes) {
            String key = CacheCommonKeys.getSysParamsKey(paramCode);

            CONF_MAP.remove(key);
        }
    }

    public void set(String paramCode, String paramValue) {
        if (paramValue == null) {
            return;
        }
        String key = CacheCommonKeys.getSysParamsKey(paramCode);
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(key);
        if (kvr == null) {
            return;
        }

        kvr.setValue(paramValue);
    }

    public String get(String paramCode) {
        String key = CacheCommonKeys.getSysParamsKey(paramCode);
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(key);
        if (kvr == null) {
            return null;
        }
        return kvr.getValue();
    }

    /**
     * 获取配置的token过期时间
     *
     * @return 配置的token过期时间 秒
     */
    public long getTokenTime() {
        String key = CacheCommonKeys.getSysParamsKey(JWT_EXPIRATION.getCode());
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(key);
        try {
            String value = kvr.getValue();
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
            return Long.parseLong(kvr.getDefaultVal());
        }
    }

    public void clear() {
        for (String s : CONF_MAP.keySet()) {
            CONF_MAP.remove(s);
        }
    }
}
