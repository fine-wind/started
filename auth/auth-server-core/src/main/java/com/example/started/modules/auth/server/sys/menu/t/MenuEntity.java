package com.example.started.modules.auth.server.sys.menu.t;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

/**
 * 系统菜单
 *
 * @since 1.0.0
 */
@Data@Entity
@Accessors(chain = true)
@Table(name = "sys_menu")
public class MenuEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    /**
     * pid
     */
    private String pid;

    private Integer type;
    /**
     * icon
     */
    private String icon;
    /**
     * 标题
     */
    private String title;
    /**
     * path
     */
    private String path;
    /**
     * 用户状态
     * 0：不显示
     * 1：正常使用
     */
    private Integer state;
    /**
     * 排序
     */
    private Integer sort;


    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private Long updater;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除标志
     */
    private Integer delFlag;
}
