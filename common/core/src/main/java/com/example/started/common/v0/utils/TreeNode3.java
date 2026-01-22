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
public interface TreeNode3 {
    String getId();

    String getPid();

    List<TreeNode3> getChildren();
}
