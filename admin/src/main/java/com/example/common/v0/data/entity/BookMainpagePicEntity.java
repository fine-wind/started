package com.example.common.v0.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 轮播图片
 *
 *
 * @since 1.0.0 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book_mainpage_pic")
public class BookMainpagePicEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 描述
     */
    private String description;
    /**
     * 图片链接
     */
    private String url;
    /**
     * 图片路径的id
     */
    private Long fileId;
    /**
     * 排序编号
     */
    private Integer sort;

}
