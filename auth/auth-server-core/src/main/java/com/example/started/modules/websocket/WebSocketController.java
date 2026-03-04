package com.example.started.modules.websocket;

import com.example.started.common.v0.utils.DateUtil;
import com.example.started.common.v0.utils.SpringContextUtils;
import com.example.started.demo.cache.RedisUtils;
import com.example.started.modules.websocket.config.*;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@Slf4j
@Component
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfig.class)
@AllArgsConstructor
public class WebSocketController {
    private static final String KEY = "ws:" + DateUtil.getStringDateShort() + ":";
    // 双向映射
    private static final ConcurrentHashMap<Session, String> SESSION_USER_MAP = new ConcurrentHashMap<>(); // Session -> userId


    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // 从config中获取用户ID (需要在WebSocketConfig中设置)
        String userId = String.valueOf(config.getUserProperties().get("userId"));
        if (userId == null) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "未授权"));
            } catch (IOException e) {
                log.error("关闭未授权连接失败", e);
            }
            return;
        }
        redis().opsSetAdd(KEY + userId, session.getId());
        SESSION_USER_MAP.put(session, userId);

        log.info("用户 [{}] 连接成功，当前连接总数: {}", userId, SESSION_USER_MAP.size());
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        String userId = SESSION_USER_MAP.remove(session);
        if (userId != null) {
            redis().opsSetRemove(KEY + userId, session.getId());

            log.info("用户 [{}] 断开连接，关闭码: {}, 原因: {}, 当前连接总数: {}",
                    userId, closeReason.getCloseCode(), closeReason.getReasonPhrase(), SESSION_USER_MAP.size());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String userId = SESSION_USER_MAP.get(session);
        log.error("用户 [{}] 连接异常: {}", userId, throwable.getMessage(), throwable);

        // 标记异常，@OnClose中会处理
        session.getUserProperties().put("error", true);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        String userId = SESSION_USER_MAP.get(session);
        if (userId == null) {
            log.warn("未知用户发送消息");
            return;
        }

        log.debug("用户 [{}] 发送消息: {}", userId, msg);

        SpringContextUtils.getBean(WebSocketService.class).onMessage(session, msg);
    }



    private RedisUtils redis() {
        return SpringContextUtils.getBean(RedisUtils.class);
    }
}