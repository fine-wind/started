package com.example.started.modules.posts;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@Table(name = "posts")
public class PostsEntity {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;
    /* 父id*/private String parentId;
    /* 主id*/private String rootId;
    /* 发布人*/
    private String fromId;
    /* 回复人*/private String toId;
    /* 回复的用户昵称*/private String toNickname;
    /* 内容*/ private String content;
    /* 文件内容*/ private String files;
    /* 状态*/private String state;
    /* 是否匿名 1是 0否*/private Integer anon;
    /* 禁止评论 1是 0否*/private Integer dis;
    /* 圈子ID*/private Integer circleId;
    /* 访问uv*/private Integer uv;
    /* 访问pv*/private Integer pv;
    /* 是否隐藏*/private Integer isHide;
    /* 最近评论时间*/private Date lastCommentAt;
    /* 最近修改时间*/private Date lastUpdateAt;
    /* 创建时间*/
    private Date createdAt;
    /* 更新时间*/ private Date updatedAt;
    /**
     * 删除标志
     * 0 未删除
     * 1 已删除
     */
    private Integer delFlag;

}
