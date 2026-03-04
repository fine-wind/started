package com.example.started.demo.modules.demo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

/**
 * 登录日志
 *
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "demo_table")
public class DemoTableEntity {
    @Id()
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    /**
     * 状态  0：失败    1：成功
     */
    private String a;
    /**
     * 用户代理
     */
    private Integer b;
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
