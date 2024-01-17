package com.example.common.v0.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点，所有需要实现树节点的，都需要继承该类
 *
 * @since 1.0.0
 */
@Data
public class TreeNode<T> implements Serializable {

    private Long id;
    private Long pid;
    /**
     * 节点左值
     */
    private Integer l;
    /**
     * 节点右值
     */
    private Integer r;

    /**
     * 子节点列表
     */
    private List<T> children = new ArrayList<>();

}
