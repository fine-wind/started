package com.example.started.common.v0.utils;

import com.example.started.common.v0.utils.to.BeanDiff;
import com.example.started.common.v0.utils.to.BeanDiffEnum;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

/**
 * 针对对象的比较
 */
public class BeanUtils2 {

    /**
     * 比较两个对象属性值是否相同,如果不同返回修改过的属性信息集合
     * 包括：字段名,原始数据值，新值，更改类型
     *
     * @param source 原始对象
     * @param target 新对象
     * @return ArrayList<BeanDiff>  变化后的数据集合
     */
    public static ArrayList<BeanDiff> compareInstance(Object source, Object target) {
        ArrayList<BeanDiff> compareResultList = new ArrayList<>();

        /*获取字段集合  */
        Map<String, Field> fileds_source = getFields(source);
        Map<String, Field> fields_target = getFields(target);
        /* 先遍历source集合，处理两种情况：  */
        /* source中有的,target中没有的->字段被删除 */
        /* source中有的,target中有但是内容变化->字段内容被更新   */
        for (Field field : fileds_source.values()) {
            BeanDiff BeanDiff = new BeanDiff();
            Object v1;
            try {
                v1 = field.get(source);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            /*    source中有的,target中没有的->字段被删除 */
            if (!fields_target.containsKey(field.getName())) {
                BeanDiff.setFieldName(field.getName());
                BeanDiff.setHanderType(BeanDiffEnum.DELETE.getCode());
                compareResultList.add(BeanDiff);
                continue;
            }
            Object v2;
            try {
                v2 = fields_target.get(field.getName()).get(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            BeanDiff.setFieldName(field.getName());
            BeanDiff.setFieldContent(v1);
            BeanDiff.setNewFieldContent(v2);
            if (v1 == null && v2 == null) {
                continue;
            }
            /*source中有的,target中有但是内容变化->字段内容被更新 */
            if (!Objects.equals(v1, v2)) {
                BeanDiff.setHanderType(BeanDiffEnum.UPDATE.getCode());
                compareResultList.add(BeanDiff);
            }
        }
        /* 遍历target集合，处理一种情况  */
        /*source中没有,target有的->新增字段 */
        for (Field field : fields_target.values()) {
            BeanDiff BeanDiff = new BeanDiff();
            if (!fileds_source.containsKey(field.getName())) {
                BeanDiff.setFieldName(field.getName());
                BeanDiff.setHanderType(BeanDiffEnum.ADD.getCode());
                compareResultList.add(BeanDiff);
            }
        }

        return compareResultList;
    }

    public static Map<String, Field> getFields(Object target) {
        if (Objects.isNull(target)) {
            return Collections.emptyMap();
        }
        Field[] declaredFields = target.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>(declaredFields.length);
        Stream.of(declaredFields).forEach(e -> {
            e.setAccessible(true);
            fieldMap.put(e.getName(), e);
        });
        return fieldMap;
    }
}
