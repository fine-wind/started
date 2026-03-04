package com.example.started.modules.websocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket统一消息体
 * 支持：聊天、通知、系统消息、心跳等
 */
@Slf4j
@Data
@NoArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebSocketMessage {

    /**
     * 消息类型
     */
    private String type;

    /**
     * 消息子类型（用于扩展）
     *
     * @see com.example.started.modules.websocket.WebSocketMessage.MessageType#CHAT_PRIVATE
     */
    private String subtype;

    /**
     * 消息ID（用于消息确认）
     */
    private String messageId;

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 消息状态
     */
    private String status;

    /**
     * 消息类型常量
     */
    public interface MessageType {
        // 聊天相关
        String CHAT = "chat";              // 聊天消息
        String CHAT_PRIVATE = "CHAT_PRIVATE"; // 私聊
        String CHAT_GROUP = "CHAT_GROUP";   // 群聊
        String CHAT_ROOM = "CHAT_ROOM";     // 聊天室
        String CHAT_ROOM_JOIN = "chatRoomJoin";     // 加入聊天室

        // 系统相关
        String SYSTEM = "SYSTEM";            // 系统消息
        String NOTICE = "NOTICE";            // 通知公告
        String ALERT = "ALERT";              // 预警提醒

        // 用户相关
        String USER_ONLINE = "USER_ONLINE";  // 用户上线
        String USER_OFFLINE = "USER_OFFLINE"; // 用户下线
        String USER_TYPING = "USER_TYPING";   // 用户正在输入
        String USER_STATUS = "USER_STATUS";   // 用户状态变更

        // 业务相关
        String TASK = "TASK";                 // 任务通知
        String ORDER = "ORDER";                // 订单通知
        String MESSAGE = "MESSAGE";            // 消息通知

        // 连接相关
        String HEARTBEAT = "HEARTBEAT";        // 心跳
        String CONNECT = "CONNECT";            // 连接成功
        String DISCONNECT = "DISCONNECT";      // 断开连接
        String ERROR = "ERROR";                 // 错误消息

        // 文件相关
        String FILE = "FILE";                   // 文件传输
        String IMAGE = "IMAGE";                  // 图片消息
        String VIDEO = "VIDEO";                  // 视频消息
        String AUDIO = "AUDIO";                  // 语音消息
    }

    /**
     * 状态常量
     */
    public interface MessageStatus {
        String SENT = "SENT";              // 已发送
        String DELIVERED = "DELIVERED";      // 已送达
        String READ = "READ";                // 已读
        String FAILED = "FAILED";            // 发送失败
        String RECALLED = "RECALLED";        // 已撤回
    }

    /**
     * 构造函数 - 聊天消息
     */
    public static WebSocketMessage createChatMessage(String senderId, String content) {
        return new WebSocketMessage()
                .setType(MessageType.CHAT)
                .setSenderId(senderId)
                .setContent(content)
                .setTimestamp(getCurrentTimestamp())
                .setMessageId(generateMessageId());
    }

    /**
     * 构造函数 - 私聊消息
     */
    public static WebSocketMessage createPrivateMessage(String senderId, String senderName,
                                                        String receiverId, String content) {
        return new WebSocketMessage()
                .setType(MessageType.CHAT_PRIVATE)
                .setSenderId(senderId)
                .setContent(content)
                .setTimestamp(getCurrentTimestamp())
                .setMessageId(generateMessageId());
    }

    /**
     * 构造函数 - 系统通知
     */
    public static WebSocketMessage createSystemNotice(String content, String level) {
        Map<String, Object> data = new HashMap<>();
        data.put("level", level); // info, warning, error
        data.put("autoClose", true);
        data.put("duration", 5000);

        return new WebSocketMessage()
                .setType(MessageType.NOTICE)
                .setSubtype("SYSTEM")
                .setSenderId("SYSTEM")
                .setContent(content)
                .setTimestamp(getCurrentTimestamp())
                .setMessageId(generateMessageId());
    }

    /**
     * 构造函数 - 任务提醒
     */
    public static WebSocketMessage createTaskNotice(String taskId, String taskName,
                                                    String assignee, String action) {
        Map<String, Object> data = new HashMap<>();
        data.put("taskId", taskId);
        data.put("taskName", taskName);
        data.put("action", action); // assigned, completed, overdue

        return new WebSocketMessage()
                .setType(MessageType.TASK)
                .setSenderId("TASK_SYSTEM")
                .setContent("您有一个任务需要处理：" + taskName)
                .setTimestamp(getCurrentTimestamp())
                .setMessageId(generateMessageId());
    }

    /**
     * 构造函数 - 预警提醒
     */
    public static WebSocketMessage createAlert(String alertType, String message,
                                               String severity, Map<String, Object> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("alertType", alertType);
        data.put("severity", severity); // high, medium, low
        data.put("details", details);
        data.put("acknowledged", false);

        return new WebSocketMessage()
                .setType(MessageType.ALERT)
                .setSubtype(alertType)
                .setSenderId("MONITOR")
                .setContent(message)
                .setTimestamp(getCurrentTimestamp())
                .setMessageId(generateMessageId());
    }

    /**
     * 构造函数 - 心跳消息
     */
    public static WebSocketMessage createHeartbeatMessage() {
        return new WebSocketMessage()
                .setType(MessageType.HEARTBEAT)
                .setTimestamp(getCurrentTimestamp())
                ;
    }

    /**
     * 构造函数 - 错误消息
     */
    public static WebSocketMessage createErrorMessage(String errorCode, String errorMessage) {

        return new WebSocketMessage()
                .setType(MessageType.ERROR)
                .setContent(errorMessage)
                .setTimestamp(getCurrentTimestamp());
    }

    /**
     * 生成消息ID
     */
    private static String generateMessageId() {
        return System.currentTimeMillis() + "_" +
                Thread.currentThread().getId() + "_" +
                (int) (Math.random() * 10000);
    }

    /**
     * 获取当前时间戳
     */
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * 转换为JSON字符串
     */
    public String toJson() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            log.error("消息转换JSON失败", e);
            return "{}";
        }
    }
}