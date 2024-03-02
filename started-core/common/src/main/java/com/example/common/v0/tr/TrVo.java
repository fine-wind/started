package com.example.common.v0.tr;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * vo 继承此类以便将字段翻译
 */
@Data
public class TrVo {
    /**
     * 翻译值
     */
    private Map<String, Object> tr = new LinkedHashMap<>();
}
