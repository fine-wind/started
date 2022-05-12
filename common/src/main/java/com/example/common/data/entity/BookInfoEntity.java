package com.example.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 图书信息表
 *
 *
 * @since 1.0.0 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("book_info")
public class BookInfoEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 图书名称
     */
    private String name;
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
     * 出版日期
     */
    private String pressDate;
    /**
     * zz时间
     */
    private String zzsj;
    /**
     * ztf分类号
     */
    private String ztfflh;
    /**
     * 封面图片
     */
    private String coverImg;
    /**
     * 主题词？
     */
    private String ztc;
    /**
     * 作品简介
     */
    private String summary;
    /**
     * 开本
     */
    private String booksize;
    /**
     * 页数
     */
    private Integer pageNum;
    /**
     * 印张yz
     */
    private String sheet;
    /**
     * ts分类号
     */
    private String tsflh;
    /**
     * 下载次数
     */
    private Integer downtimes;
    /**
     * 阅读次数
     */
    private Integer readtimes;
    /**
     * 收藏数量
     */
    private Integer ordernum;
    /**
     * 图书存储路径wz
     */
    private String filepath;
    /**
     * 搜索次数？
     */
    private Integer bsearch;
    /**
     * 图书代码？
     */
    private String tsdm;
    /**
     * 下载统计？
     */
	private String xztj;
    /**
     * 是否必读
     */
    private Integer mustRead;
    /**
     * 是否推荐
     */
    private Integer recommended;
    /**
     * 一级分类id
     */
    private Long categoryId1;
    /**
     * 一级分类名称
     */
    private String categoryName1;
    /**
     * 2级分类id
     */
    private Long categoryId2;
    /**
     * 2级分类名称
     */
    private String categoryName2;
    /**
     * 评分星数
     */
    private BigDecimal starnum;
    /**
     * 推荐次数
     */
    private Integer recmdtimes;
}
