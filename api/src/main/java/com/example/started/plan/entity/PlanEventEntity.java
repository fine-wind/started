package com.example.started.plan.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

/**
 * 计划任务
 *
 * @since 1.0.0
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "plan_event")
public class PlanEventEntity {
    @Id()
//    @GeneratedValue(generator = "uuid")
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    private Date dt;
    private String text;
    private String color;
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
