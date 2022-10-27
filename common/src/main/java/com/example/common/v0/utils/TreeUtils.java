package com.example.common.v0.utils;

import com.example.common.v0.domain.TreeInterface;
import com.example.common.v0.validator.AssertUtils;

import java.util.*;
import java.util.function.Function;

/**
 * 树形结构工具类，如：菜单、部门等
 *
 * @since 1.0.0
 */
public class TreeUtils {

    /**
     * 根据pid，构建树节点
     */
    @Deprecated
    public static <T extends TreeNode> List<T> build(List<T> treeNodes, String pid) {
        //pid不能为空
        AssertUtils.isNull(pid, "pid");

        List<T> treeList = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (pid.equals(String.valueOf(treeNode.getPid()))) {
                treeList.add(findChildren(treeNodes, treeNode));
            }
        }

        return treeList;
    }

    /**
     * 查找子节点
     */
    private static <T extends TreeNode> T findChildren(List<T> treeNodes, T rootNode) {
        for (T treeNode : treeNodes) {
            if (rootNode.getId().equals(treeNode.getPid())) {
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     *
     * @see TreeUtils#build(java.util.List, java.util.function.Function, java.util.function.Function)
     */
    @Deprecated
    public static <T extends TreeNode> List<T> build(List<T> treeNodes) {
        List<T> result = new ArrayList<>();

        //list转map
        Map<String, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(String.valueOf(treeNode.getId()), treeNode);
        }

        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(String.valueOf(node.getPid()));
            if (Objects.nonNull(parent)) {
                parent.getChildren().add(node);
                continue;
            }

            result.add(node);
        }

        return result;
    }

    /**
     * 使用泛型 动态生成
     *
     * @param list     待处理的集合列表
     * @param id       获取元素id方法
     * @param parentId 获取元素父id方法
     * @param <T>      元素类型
     * @return 构建完毕的树结构
     */
    public static <T extends TreeInterface> List<T> build(List<T> list, Function<T, Object> id, Function<T, Object> parentId) {
        List<T> result = new ArrayList<>();
        Map<Object, T> map = new HashMap<>(list.size());
        list.forEach(e -> map.put(id.apply(e), e));
        list.forEach(e -> {
            if (map.containsKey(parentId.apply(e))) {
                map.get(parentId.apply(e)).getChildren().add(e);
            } else {
                result.add(e);
            }
        });
        return result;
    }
}
