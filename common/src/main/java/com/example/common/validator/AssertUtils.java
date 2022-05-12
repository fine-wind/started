package com.example.common.validator;

import com.example.common.exception.UniversalCode;
import com.example.common.exception.ServerException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 校验工具类
 *
 * @since 1.0.0
 */
public class AssertUtils {

    public static void isBlank(String str, String... params) {
        isBlank(str, UniversalCode.NOT_NULL, params);
    }

    public static void isBlank(String str, Integer code, String... params) {
        if (code == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "code");
        }

        if (StringUtils.isBlank(str)) {
            throw new ServerException(code, params);
        }
    }

    public static void isNull(Object object, String... params) {
        isNull(object, UniversalCode.NOT_NULL, params);
    }

    public static void isNull(Object object, Integer code, String... params) {
        if (code == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "code");
        }

        if (object == null) {
            throw new ServerException(code, params);
        }
    }

    public static void isArrayEmpty(Object[] array, String... params) {
        isArrayEmpty(array, UniversalCode.NOT_NULL, params);
    }

    public static void isArrayEmpty(Object[] array, Integer code, String... params) {
        if (code == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "code");
        }

        if (Objects.isNull(array) || array.length == 0) {
            throw new ServerException(code, params);
        }
    }

    public static void isListEmpty(List<?> list, String... params) {
        isListEmpty(list, UniversalCode.NOT_NULL, params);
    }

    public static void isListEmpty(List<?> list, Integer code, String... params) {
        if (code == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "code");
        }

        if (Objects.isNull(list) || list.isEmpty()) {
            throw new ServerException(code, params);
        }
    }

    public static void isMapEmpty(Map map, String... params) {
        isMapEmpty(map, UniversalCode.NOT_NULL, params);
    }

    public static void isMapEmpty(Map map, Integer code, String... params) {
        if (code == null) {
            throw new ServerException(UniversalCode.NOT_NULL, "code");
        }

        if (Objects.isNull(map) || map.isEmpty()) {
            throw new ServerException(code, params);
        }
    }
}
