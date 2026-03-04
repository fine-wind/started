package com.example.started.modules.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomCode;
    private String toWho;
    private String message;
}
