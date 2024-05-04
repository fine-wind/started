package com.example.common.v1.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

/**
 * 树形结构工具类
 *
 * @since 1.0.0.2024年5月5日
 */
public class TreeUtils {

    /**
     * 树节点，所有需要实现树节点的
     *
     * @since 1.0.0
     */
    @Data
    @Accessors(chain = true)
    public static class TreeNode<T> implements Serializable {

        private T obj;

        /**
         * 子节点列表
         */
        private List<TreeNode<T>> children = new ArrayList<>();

    }

    /**
     * 通用构建树结构的方法
     *
     * @param list 待处理的集合
     * @param id   获取集合id的方法
     * @param pid  获取集合父id的方法
     * @param <T>  集合元素类型
     * @param <S>  父子关联类型
     * @return
     */
    public static <T, S> List<TreeNode<T>> buildTree(List<T> list, Function<T, S> id, Function<T, S> pid) {
        Map<S, TreeNode<T>> map = new HashMap<>();
        for (T info : list) {
            map.put(id.apply(info), new TreeNode<T>().setObj(info));
        }
        List<TreeNode<T>> tree = new LinkedList<>();
        for (T info : list) {
            S parentID = pid.apply(info);
            if (map.containsKey(parentID)) {
                map.get(parentID).getChildren().add(map.get(id.apply(info)));
            } else {
                tree.add(map.get(id.apply(info)));
            }
        }
        return tree;
    }
}
