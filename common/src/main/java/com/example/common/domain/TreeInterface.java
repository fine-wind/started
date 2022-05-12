package com.example.common.domain;

import java.util.List;

/**
 * Tree基类
 *
 * @author author
 */
public interface TreeInterface {
    long serialVersionUID = 132891029031242331L;

    /**
     * 子数据
     */
    List<TreeInterface> getChildren();
}
