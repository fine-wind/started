package com.example.started.modules.posts;

import lombok.Data;

@Data
public class PostsFindVo {

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
    private String createdAt;
}
