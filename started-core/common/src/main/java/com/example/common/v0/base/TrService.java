package com.example.common.v0.base;

import java.util.Map;

public interface TrService {
    Map<String, Object> selectValueByTableAndColumn(String column, String table, String key, String valueOf);
}
