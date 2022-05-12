package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户行为记录
 *
 * @since 1.0.0 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book_user_behavior")
public class BookUserBehaviorEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 操作人的用户id
     */
    private Long userId;
    /**
     * 操作人的用户name
     */
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String headUrl;
    /**
     * 类别：1阅读、2收藏、3推荐、4下载、5书评
     */
    private String type;
    /**
     * book_id
     */
    private Long bookId;
    /**
     * 图书名称
     */
    private String bookName;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String press;
    /**
     * isbn
     */
    private String isbn;
    /**
     * ip
     */
    private String ip;
    /**
     * 操作时间
     */
    private Date actDate;
    /**
     * 时长(单位:分钟)
     */
    private Integer readtime;
    /**
     * 图书分类id（一级）
     */
    private Long categoryId1;
    /**
     * 图书名称（一级）
     */
    private String categoryName1;
    /**
     * 图书分类id（二级）
     */
    private Long categoryId2;
    /**
     * 图书名称（二级）
     */
    private String categoryName2;
    /**
     * 备注
     */
    private String remark;


    @TableField(exist = false)
    private BookInfoEntity bookInfo;
}
