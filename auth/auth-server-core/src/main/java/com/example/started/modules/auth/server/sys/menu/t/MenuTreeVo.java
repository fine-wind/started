package com.example.started.modules.auth.server.sys.menu.t;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.started.common.v0.utils.TreeNode;
import com.example.started.common.v0.utils.TreeNode2;
import com.example.started.common.v0.utils.TreeNode3;
import com.example.started.dynamic.datasource.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单
 */
@Data
@NoArgsConstructor
public class MenuTreeVo implements TreeNode3 {
    /**
     * id
     */
    private String id;
    /**
     * pid
     */
    private String pid;
    /**
     * icon
     */
    private String icon;
    /**
     * 标题
     */
    private String title;
    /**
     * 用户状态
     * 0：不显示
     * 1：正常使用
     */
    private Integer state;

    private List<TreeNode3> children = new ArrayList<>();

    public MenuTreeVo(MenuEntity menuEntity) {
        this.id = String.valueOf(menuEntity.getId());
        this.pid = String.valueOf(menuEntity.getPid());
        this.icon = menuEntity.getIcon();
        this.title = menuEntity.getTitle();
        this.state = menuEntity.getState();
    }
}
