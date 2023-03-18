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
public class TreeNode implements Serializable {
    long serialVersionUID = 1L;

    private Long id;
    private Long pid;

    /**
     * 子节点列表
     */
    private List<TreeNode> children = new ArrayList<>();

}
