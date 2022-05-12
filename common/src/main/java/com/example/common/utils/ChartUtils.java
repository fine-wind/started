package com.example.common.utils;

import com.example.common.annotation.Chart;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * 基本chat
 */
public class ChartUtils {

    /**
     * @param x     x轴
     * @param list  y轴
     * @param fun   y轴按照哪个统计
     * @param famat y轴统计格式化
     */
    public static <T> Map<String, Object> lineStack(List<String> x, List<T> list, Function<T, Object> fun, Function<T, String> famat) {
        Map<String, Integer> xMap = new HashMap<>(x.size());
        AtomicInteger i = new AtomicInteger();
        x.forEach(t -> xMap.put(t, i.getAndIncrement()));
        /* 初始化结构*/
        Map<String, Object> result = new HashMap<>(2);
        result.put("x", x);
        List<Line> y = new ArrayList<>();
        result.put("y", y);
        /* 初始化数据*/
        if (Objects.isNull(list) || list.isEmpty()) {
            return result;
        }
        List<Field> tempFields = new ArrayList<>();
        /* */
        {
            Class<?> clazz = list.get(0).getClass();
            tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
            tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        Map<String, Field> fieldMap = new HashMap<>();

        final List<Chart> fields = new ArrayList<>();

        for (Field field : tempFields) {
            if (field.isAnnotationPresent(Chart.class)) {
                Chart annotation = field.getAnnotation(Chart.class);
                if (Objects.nonNull(annotation)) {
                    fields.add(annotation);
                    field.setAccessible(true);
                    fieldMap.put(annotation.title(), field);
                }
            }
        }
        int size = fields.size();
        /* 初始化空数据*/
        Map<String, List<Object>> seriesMap = new HashMap<>(size);
        for (Chart field : fields) {
            String title = field.title();
            Line e = new Line(title, new ArrayList<>());
            y.add(e);
            x.forEach(t -> e.getData().add(null));
            seriesMap.put(title, e.getData());
        }
        /* 填充实际数据*/
        for (T t : list) {
            fields.forEach(f -> {
                Field field = fieldMap.get(f.title());
                try {
                    Object o = field.get(t);
                    Object x_ = fun.apply(t);
                    if (Objects.nonNull(famat)) {
                        // todo 处理格式化
                    }
                    String xK = String.valueOf(x_);
                    if (xMap.containsKey(xK)) {
                        Integer integer = xMap.get(xK);
                        seriesMap.get(f.title()).set(integer, o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }

        return result;
    }

    @Data
    @AllArgsConstructor
    private static class Line {
        private String name;
        private List<Object> data;
    }
}
