package com.example.common.utils;

import com.example.common.annotation.DictTranslationClass;
import com.example.common.annotation.DictTranslationField;
import com.example.common.constant.Constant;
import com.example.common.data.page.PageData;
import com.example.common.modules.sys.dict.dao.SysDictDataDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 翻译翻译
 */
@Log4j2
@Service
public class Translation {
    @Autowired
    private SysDictDataDao dictDataDao;

    /**
     * 翻译
     *
     * @param body 待翻译的数据
     */
    public void tr(Object body) {
        if (body instanceof Result) {
            Result<Object> result = (Result) body;
            if (result.isSuccess()) {
                Object data = result.getData();
                if (Objects.nonNull(data)) {
                    data = this.translation(data, new HashMap<>());
                    result.setData(data);
                }
            }
        }
    }

    /**
     * 值不存在时获取该类型的默认值
     *
     * @param aClass 类型
     * @param value  值
     * @return 默认值
     */
    private Object defaultFormat(Class<?> aClass, Object value) {
        if (Objects.isNull(aClass)) {
            return value;
        }
        boolean isNull = Objects.isNull(value);
        value = !isNull && Long.class.equals(aClass) ? value.toString() : value;
        value = isNull && String.class.equals(aClass) ? "" : value;
        value = isNull && List.class.equals(aClass) ? new ArrayList<>(0) : value;
        return value;
    }

    /**
     * 翻译一个对象
     *
     * @param data 对象
     * @return 翻译后的结果
     */
    private Object translation(Object data, Map<String, String> cacheMap) {
        if (Objects.isNull(data) || data instanceof Integer || data instanceof Long || data instanceof String) {
            return data;
        }

        if (data instanceof PageData) {
            PageData pageData = (PageData) data;
            List<?> list = pageData.getList();

            List<Object> listRes = new ArrayList<>(list.size());
            for (Object o : list) {
                listRes.add(translationDict(o.getClass(), o, null, cacheMap));
            }
            pageData.setList(listRes);
            return pageData;
        } else if (data instanceof List) {
            List res = (List) data;
            List<Object> list = new ArrayList<>(res.size());
            for (Object obj : res) {
                Object o = translationDict(obj.getClass(), obj, null, cacheMap);
                list.add(o);
            }
            return list;
        } else if (data instanceof Map) {
            return data;
        } else if (data instanceof Set) {
            return data;
        }
        return translationDict(data.getClass(), data, null, cacheMap);
    }


    private Object translationDict(Class<?> clazz, Object obj, Map<String, Object> objectMap, Map<String, String> cacheMap) {
        if (Objects.isNull(clazz) || Objects.isNull(obj)) {
            return objectMap;
        }
        /* 如果此类不需要翻译，就跳过*/
        DictTranslationClass dtc = obj.getClass().getAnnotation(DictTranslationClass.class);
        if (Objects.isNull(dtc)) {
            return obj;
        }
        if (!dtc.translation()) {
            return obj;
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        if (Objects.isNull(objectMap)) {
            objectMap = new HashMap<>(declaredFields.length);
        }
        for (Field field : declaredFields) {
            DictTranslationField annotation = field.getAnnotation(DictTranslationField.class);
            String name = field.getName();
            switch (name) {
                case Constant.TABLE.CREATOR:
                case Constant.TABLE.CREATE_DATE:
                case Constant.TABLE.UPDATER:
                case Constant.TABLE.UPDATE_DATE:
                case Constant.TABLE.DEL_FLAG:
                    continue;
            }
            if (name.contains("serialVersionUID") || name.contains("password")) {
                continue;
            }

            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                log.error(e);
            }
            Class<?> type = field.getType();
            value = defaultFormat(type, value);
            value = translation(value, cacheMap);
            // log.info("{}#{} type: {} {}", clazz, name, field.getGenericType(), value);
            if (Objects.isNull(objectMap.get(name))) {
                objectMap.put(name, value);
            }
            if (annotation == null) {
                continue;
            }
            String s = Objects.nonNull(value) ? dt(annotation, value, cacheMap) : StringUtil.NULL_STRING;
            log.info("翻译的字段：{} -> {}", name, s);
            objectMap.put(name + "_dict", s);
        }
        translationDict(clazz.getSuperclass(), obj, objectMap, cacheMap);
        return objectMap;
    }

    /**
     * 根据注解获取翻译
     *
     * @param annotation 注解
     * @param value      待翻译数据
     * @return 翻译后的数据
     */
    private String dt(DictTranslationField annotation, Object value, Map<String, String> cacheMap) {
        String dictCode = annotation.dictCode();
        String table = annotation.table();
        String column = annotation.column();
        String key = annotation.key();
        String k = dictCode + table + column + value + key;
        /* 如果存在 就直接返回了*/
        if (cacheMap.containsKey(k)) {
            return cacheMap.getOrDefault(k, StringUtil.NULL_STRING);
        }

        String val;
        if (StringUtil.isEmpty(dictCode)) {
            /* 替换空格用于防注入*/
            column = column.replaceAll(" ", StringUtil.NULL_STRING);
            table = table.replaceAll(" ", StringUtil.NULL_STRING);
            key = key.replaceAll(" ", StringUtil.NULL_STRING);

            val = dictDataDao.selectValueByTableAndColumn(column, table, key, String.valueOf(value));
        } else {
            // todo 是字典时的翻译
            // dictCode = dictCode.replaceAll(" ",StringUtil.NULL_STRING);
            val = null;
        }
        cacheMap.put(k, val);
        return val;
    }

}
