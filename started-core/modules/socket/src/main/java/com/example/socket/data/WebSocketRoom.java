package com.example.socket.data;

import lombok.Data;

import jakarta.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket连接数据
 */
@Data
public class WebSocketRoom {
    /**
     * 房间里的顾客
     * sessionId: session
     */
    private Map<String, Session> customers = new HashMap<>();
}
