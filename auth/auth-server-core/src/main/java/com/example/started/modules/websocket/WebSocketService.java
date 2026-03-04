package com.example.started.modules.websocket;

import com.example.started.common.v0.utils.SpringContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * WebSocket服务
 */
@Slf4j
@Component
@AllArgsConstructor
public class WebSocketService {
    @SneakyThrows
    public void onMessage(Session session, String message) {
        Map<String, WebSocketHandle> beansOfType = SpringContextUtils.getBeansOfType(WebSocketHandle.class);
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketMessage webSocketMessage = objectMapper.readValue(message, WebSocketMessage.class);
        beansOfType.getOrDefault(webSocketMessage.getType(), null).handleMessage(session, webSocketMessage);
    }

    /**
     * 发送消息
     */
    public boolean sendMessage(Session session, WebSocketMessage message) {
        if (session == null || !session.isOpen()) {
            return false;
        }
        // session.getBasicRemote().sendText(JsonUtils.toJsonString(message));
        return true;
    }
}