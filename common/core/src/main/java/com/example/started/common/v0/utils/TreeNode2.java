package com.example.started.common.v0.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点，所有需要实现树节点的，都需要继承该类
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class TreeNode2<T> implements Serializable {
    /**
     * 本节点
     */
    private T t;
    /**
     * 子节点列表
     */
    private List<TreeNode2<T>> children = new ArrayList<>();

    public TreeNode2(T t) {
        this.t = t;
    }
}
