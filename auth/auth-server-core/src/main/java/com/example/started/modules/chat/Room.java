package com.example.started.modules.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 开房对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    private String roomId; // 房间id
    private String creator; // 创建者ID
    private String createTime; // 创建时间戳
    private String maxMembers;    // 最大成员数
    private String status; // 房间状态
    private String announcement;// 房间公告
    private String password; // 房间密码
    private String allowAnonymous; // 是否允许匿名
    private Integer speakInterval; // 发言间隔(秒)


}
