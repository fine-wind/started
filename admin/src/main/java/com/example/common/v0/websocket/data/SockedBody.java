package com.example.common.v0.websocket.data;

import lombok.Data;

/**
 * socked的交互对象
 */
@Data
public class SockedBody {

    /**
     * 消息id
     */
    private Long id;
    /**
     * 谁发的消息
     */
    private String from;
    /**
     * 发送到哪里
     */
    private String to;
    /**
     * 消息类型 api text
     */
    private String type;
    /**
     * 消息内容
     */
    private String msg;

}
