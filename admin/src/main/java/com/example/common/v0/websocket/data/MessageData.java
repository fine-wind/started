package com.example.common.v0.websocket.data;

import lombok.Data;

/**
 * 响应客户端数据
 */
@Data
public class MessageData<T> {
    /**
     * 编码  0：文本消息  1：对象消息
     */
    private int type = 0;
    /**
     * 文本消息
     */
    private String msg;
    /**
     * 对象消息
     */
    private T data;

    public MessageData<T> data(T data) {
        this.setData(data);
        this.type = 1;
        return this;
    }

    public MessageData<T> msg(String msg) {
        this.msg = msg;
        return this;
    }
}
