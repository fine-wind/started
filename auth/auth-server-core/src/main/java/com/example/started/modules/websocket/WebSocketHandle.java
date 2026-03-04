package com.example.started.modules.websocket;

import jakarta.websocket.Session;

/**
 * WebSocket服务
 */
public interface WebSocketHandle {
    void handleMessage(Session session, WebSocketMessage webSocketMessage);
}