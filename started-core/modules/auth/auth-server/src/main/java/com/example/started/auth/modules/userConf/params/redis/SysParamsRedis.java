package com.example.started.auth.modules.userConf.params.redis;

import com.example.common.v0.constant.Constant;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.common.v0.constant.Constant.PARAM_CONF.APP_SETTINGS_CONF.*;

/**
 * 参数管理
 *
 * @since 1.0.0
 */
@Service
public class SysParamsRedis {

    public void delete(String[] paramCodes) {
        for (String paramCode : paramCodes) {
            CONF_MAP.remove(paramCode);
        }
    }

    public void set(String paramCode, String paramValue) {
        if (paramValue == null) {
            return;
        }
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(paramCode);
        if (kvr == null) {
            return;
        }

        kvr.setValue(paramValue);
    }

    public String get(String paramCode) {
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(paramCode);
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
        String key = JWT_EXPIRATION.name();
        Constant.PARAM_CONF.KVR kvr = CONF_MAP.get(key);
        if (Objects.isNull(kvr)) {
            return Long.parseLong(JWT_EXPIRATION.getKvr().getValue());
        }
        String value = kvr.getValue();
        try {
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
