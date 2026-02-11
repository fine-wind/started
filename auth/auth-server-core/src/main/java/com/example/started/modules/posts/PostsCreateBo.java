package com.example.started.modules.posts;

import com.example.started.common.v0.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PostsCreateBo {

    /**
     * 标题
     */
    private String title;
    /**
     * 概要
     */
    private String summary;
    /**
     * 标签
     */
    private Set<String> tags;
    private String category;
    private String content;
    /**
     * 状态
     */
    private String status;
    /**
     * 搜索的时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME)
    private Date lastDt;
}
