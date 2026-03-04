package com.example.started.modules.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostsInfoBo {
    /**
     * 帖子ip
     */
    private String id;
    /**
     * 请求ip
     */
    private String ip;
}
