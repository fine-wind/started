package com.example.started.common.v0.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 处理翻译
 *
 * @author xing
 */
public class TranslateUtils {

    /**
     * 数据库关联时存的外键
     * 显示给前端时 不显示外键 而是显示外键所在表的另一个字符串属性的值
     *
     * @param list     源列表
     * @param function 源列表元素翻译哪个字段
     * @param clz      所属字段用哪个Service
     * @param entity   Service 获取实体的方法 可以给此function 添加注解
     * @param value    用实体哪个字段 不传时 用整个实体
     * @param bb       翻译后的key
     * @param <T>      待翻译类型
     * @param <M>      获取的key
     * @param <S>      service的类
     * @param <R>      service查询结果的类型
     */
    public static <T, M, S, R> void setName(List<T> list,
                                            Function<T, M> function,
                                            Class<S> clz,
                                            BiFunction<S, M, R> entity,
                                            Function<R, String> value,
                                            BiConsumer<T, String> bb) {
        if (Objects.isNull(list) || Objects.isNull(function)) {
            return;
        }
        S bean = SpringContextUtils.getBean(clz);
        list.forEach(e -> {
            M foreignKey = function.apply(e);
            R apply;
            if (Objects.isNull(foreignKey) || Objects.isNull((apply = entity.apply(bean, foreignKey)))) {
                return;
            }
            String apply1 = value.apply(apply);
            bb.accept(e, apply1);
        });
    }

    public static <T, M, S> void setName(List<T> list,
                                         Function<T, M> function,
                                         Class<S> clz,
                                         BiFunction<S, M, String> services,
                                         BiConsumer<T, String> bb) {
        if (Objects.isNull(list) || Objects.isNull(function)) {
            return;
        }
        S bean = SpringContextUtils.getBean(clz);
        list.forEach(e -> {
            M foreignKey = function.apply(e);
            String apply;
            if (Objects.isNull(foreignKey) || Objects.isNull((apply = services.apply(bean, foreignKey)))) {
                return;
            }
            bb.accept(e, apply);
        });
    }

}
