package com.example.started.modules.posts;

import com.example.started.common.v0.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PostsFindBo {

    /**
     * 模糊搜索
     */
    private String title;

    /**
     * 搜索类型
     */
    private String searchType;
    /**
     * 搜索的时间
     */
    @JsonFormat(pattern = DateUtil.DATE_TIME)
    private Date lastDt;
}
