package com.example.common.v0.utils;

import com.example.common.v0.validator.AssertUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, String pid) {
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
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode) {
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
     * @param list 待构建的集合
     * @param <T>  类型
     * @return 构建完毕的树
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> list) {
        List<T> result = new ArrayList<>();
        Map<Object, T> map = new HashMap<>(list.size());
        list.forEach(e -> map.put(e.getId(), e));
        list.forEach(e -> {
            Object pid = e.getPid();
            if (map.containsKey(pid)) {
                map.get(pid).getChildren().add(e);
            } else {
                result.add(e);
            }
        });
        return result;
    }


    /**
     * 原地算法 设置l和r
     *
     * @param list     list 树形结构集合
     * @param lr       lr的原子计数
     * @param parentId l and r change callback
     * @param <T>      <T>  类型
     */
    public static <T extends TreeNode<T>> void setLr(List<T> list, AtomicInteger lr, Consumer<T> parentId) {
        list.forEach(e -> {
            Integer oldL = e.getL();
            e.setL(lr.getAndIncrement());
            setLr(e.getChildren(), lr, parentId);
            Integer oldR = e.getR();
            e.setR(lr.getAndIncrement());
            if (Objects.nonNull(parentId) && (!Objects.equals(oldL, e.getL()) || !Objects.equals(oldR, e.getR()))) {
                parentId.accept(e);
            }
        });
    }
}
