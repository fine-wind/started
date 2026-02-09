package com.example.started.modules.posts;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PostsFindBo {

    /* 父id*/private String parentId;
    /* 主id*/private String rootId;
    /* 发布人*/ private String fromId;
    /* 回复人*/private String toId;
    /* 内容*/ private String content;
    /**
     * 搜索的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastDt;
}
