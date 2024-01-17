package com.example.common.v0.websocket.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.websocket.Session;

/**
 * WebSocket连接数据
 */
@Data
@AllArgsConstructor
public class WebSocketData {
    /**
     * 用户名
     */
    private String username;

    /**
     * 他的session
     */
    private Session session;
}
