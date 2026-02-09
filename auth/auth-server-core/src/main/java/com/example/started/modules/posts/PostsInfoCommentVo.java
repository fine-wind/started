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
    private Date createdAt;
}
