package com.example.common.v0.tr;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.v0.base.TrService;
import com.example.common.v0.data.page.PageData;
import com.example.common.v0.utils.StringUtil;
import com.example.common.v1.annotation.Ti;
import com.example.common.v1.annotation.TiField;
import com.example.common.v3.cache.RedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 翻译翻译
 */
@Slf4j
@Service
@AllArgsConstructor
public class Translation {
    private final TrService dictDataDao;
    private final RedisUtils redisUtils;

    /**
     * 翻译一个对象
     *
     * @param data 对象
     */
    public void trRoot(Object data) {
        if (Objects.isNull(data) || data instanceof Number || data instanceof String) {
            return;
        }

        switch (data) {
            case PageData<?> pageData -> {
                List<?> list = pageData.getList();
                list.forEach(o -> translationDict(o.getClass(), o));
            }
            case Map<?, ?> m -> m.values().forEach(o -> translationDict(o.getClass(), o));
            case Iterable<?> d -> d.forEach(o -> translationDict(o.getClass(), o));
            default -> this.translationDict(data.getClass(), data);
        }
    }

    /**
     * @param obj 对象
     */
    private void translationDict(Class<?> clazz, Object obj) {
        if (Objects.isNull(obj) || Objects.isNull(clazz)) {
            return;
        }

        /* 如果此类不需要翻译，就跳过*/
        if (!(obj instanceof TrVo trVo)) {
            return;
        }

        // MethodType methodType = MethodType.methodType(clazz, int.class, long.class);// 有多个字段怎么办？

        /* 所以字段的集合*/
        Field[] declaredFields = clazz.getDeclaredFields();
        /* v2 使用全注解的形式处理*/
        {
            for (Field field : declaredFields) {
                TiField tiField = field.getAnnotation(TiField.class);
                if (tiField == null) {
                    continue;
                }
                List<String> columnField = new LinkedList<>();
                Field[] tableFields = tiField.table().getDeclaredFields();
                for (Field field1 : tableFields) {
                    Ti ti = field1.getAnnotation(Ti.class);
                    if (Objects.nonNull(ti)) {
                        if (Arrays.asList(ti.column()).contains(clazz))
                            columnField.add(field1.getName());
                        // todo 此处如果用注解改变了数据库列名怎么办？
                    }
                }
                if (!columnField.isEmpty()) {
                    field.setAccessible(true);
                    Object value = null;
                    try {
                        value = field.get(obj);
                    } catch (IllegalAccessException e) {
                        log.error("翻译出错", e);
                    }
                    // log.info("翻译的字段：{} -> {}", name, s);
                    TableName tableName = tiField.table().getAnnotation(TableName.class);
                    this.idt(tiField, tableName, columnField, value)
                            .forEach((k, v) -> trVo.getTr().put(field.getName() + "_" + k, v));
                }
            }
        }
    }

    /**
     * 根据注解获取翻译
     *
     * @param annotation  注解
     * @param tableName   注解
     * @param columnField .
     * @param value       待翻译数据
     * @return .
     */
    private Map<String, Object> idt(TiField annotation, TableName tableName, List<String> columnField, Object value) {
        if (Objects.isNull(value)) {
            return new HashMap<>(0, 0);
        }
        /* 变换驼峰*/
        columnField.replaceAll(StringUtil::humpTo_);
        /* 缓存的key*/
        String cacheKey = tableName.value() + ':' + annotation.key();
        Map<String, Object> stringStringMap = new HashMap<>(columnField.size(), 1);
        boolean isSelect = false;
        Map<String, Object> stringObjectMap = redisUtils.hashGetAll(cacheKey);
        for (String k : columnField) {
            Object v = stringObjectMap.get(k);
            isSelect = isSelect || Objects.isNull(v);
            stringStringMap.put(k, v);
        }
        /* 查询数据库*/
        if (isSelect) {
            Map<String, Object> t = dictDataDao.selectValueByTableAndColumn(String.join(",", columnField), tableName.value(), annotation.key(), String.valueOf(value));
            if (Objects.nonNull(t)) {
                stringStringMap.putAll(t);
            }
            redisUtils.hashMSet(cacheKey, stringStringMap);
        }
        return stringStringMap;
    }

}
