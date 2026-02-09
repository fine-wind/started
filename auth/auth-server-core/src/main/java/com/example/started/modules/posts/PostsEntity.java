package com.example.started.modules.posts;

import com.baomidou.mybatisplus.annotation.*;
import com.example.started.common.v0.utils.Result;
import com.example.started.dynamic.datasource.TableConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;

@Data
@TableName("posts")
public class PostsEntity {

    /**
     * id
     */
    @TableId()
    private String id;
    /* 父id*/private String parentId;
    /* 主id*/private String rootId;
    /* 发布人*/
    @TableField(fill = FieldFill.INSERT)
    private String fromId;
    /* 回复人*/private String toId;
    /* 回复的用户昵称*/private String toNickname;
    /* 内容*/ private String content;
    /* 文件内容*/ private String files;
    /* 状态*/private String state;
    /* 是否匿名*/private Integer anon;
    /* 禁止评论*/private Integer dis;
    /* 圈子ID*/private Integer circleId;
    /* 访问uv*/private Integer uv;
    /* 访问pv*/private Integer pv;
    /* 是否隐藏*/private Integer isHide;
    /* 最近评论时间*/private Date lastCommentAt;
    /* 最近修改时间*/private Date lastUpdateAt;
    /* 创建时间*/
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;
    @TableField(fill = FieldFill.UPDATE)
    /* 更新时间*/ private Date updatedAt;
    /**
     * 删除标志
     */
    @TableLogic(value = TableConstant.DEL_FLAG.SHOW, delval = TableConstant.DEL_FLAG.DEL)
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

}
