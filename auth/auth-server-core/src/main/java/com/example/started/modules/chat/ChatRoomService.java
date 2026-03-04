package com.example.started.modules.chat;

import com.example.started.demo.cache.RedisUtils;
import com.example.started.modules.websocket.WebSocketHandle;
import com.example.started.modules.websocket.WebSocketMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service(WebSocketMessage.MessageType.CHAT)
@AllArgsConstructor
public class ChatRoomService implements WebSocketHandle {
    private final RedisUtils redisUtils;

    /**
     * 开房
     */
    public void createRoom(Room room) {

    }

    /**
     * 加入房间
     *
     * @param roomId 房间id
     * @param userId 用户id
     */
    public void joinRoom(String roomId, String userId) {
        redisUtils.opsSetAdd(roomId, userId);
    }

    /**
     * 在房间里喊话
     *
     * @param roomId  房间id
     * @param message 消息
     */
    public void leaveRoom(String roomId, ChatMessage message) {
    }

    /**
     * 离开房间
     *
     * @param roomId 房间id
     * @param userId 用户id
     */
    public void leaveRoom(String roomId, String userId) {
    }

    @Override
    @SneakyThrows
    public void handleMessage(Session session, WebSocketMessage webSocketMessage) {

        ObjectMapper objectMapper = new ObjectMapper();
        String content = webSocketMessage.getContent();
        ChatMessage chatMessage = objectMapper.readValue(content, ChatMessage.class);

        switch (webSocketMessage.getSubtype()) {
            case WebSocketMessage.MessageType.CHAT_ROOM_JOIN:
                redisUtils.opsSetAdd("chat:room:" + chatMessage.getRoomCode(), webSocketMessage.getSenderId());
        }
        session.getBasicRemote().sendText(WebSocketMessage.createChatMessage("_", "来了").setSubtype(WebSocketMessage.MessageType.CHAT_ROOM_JOIN).toJson());
    }
}
