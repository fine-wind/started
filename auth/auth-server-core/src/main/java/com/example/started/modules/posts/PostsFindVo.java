package com.example.started.modules.posts;

import com.example.started.common.v0.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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

    @JsonFormat(pattern = DateUtil.DATE_TIME)
    private Date createdAt;
}
