package com.example.started.modules.posts;

import lombok.Data;

import java.util.Date;

@Data
public class PostsInfoCommentVo {

    /**
     * id
     */
    private String id;
    /**
     * 内容
     */
    private String content;


    private Integer uv;
    private Integer pv;
    /* 回复的用户昵称*/private String toNickname;
    private Date createdAt;
}
