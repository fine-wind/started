package com.example.common.websocket.service;

import com.alibaba.fastjson.JSON;
import com.example.common.asyn.CachedThreadPool;
import com.example.common.constant.Constant;
import com.example.common.security.JwtUtils;
import com.example.common.utils.BeanHeaderUtils;
import com.example.common.utils.Result;
import com.example.common.utils.StringUtil;
import com.example.common.websocket.WebSocketRequestServer;
import com.example.common.websocket.data.SockedBody;
import com.example.common.websocket.data.WebSocketData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket连接数据
 */
@Slf4j
@Service
public class WsService {

    @Autowired
    private ChatService chatService;

    /**
     * 客户端连接信息
     * username: session
     */
    protected static final Map<String, WebSocketData> users = new ConcurrentHashMap<>();
    /**
     * sessionId: username
     */
    protected static final Map<String, String> sessionUsername = new ConcurrentHashMap<>();

    public void open(Session session, String token) {

        String username;
        try {
            Claims decoder = JwtUtils.decoder(token);
            username = decoder.getSubject();
        } catch (Exception e) {
            log.error("连接ws时token失效");
            Result<?> result = new Result<>().error(Constant.UniversalCode.UNAUTHORIZED, "登录失效");
            sendMessage(session, result);
            return;
        }
        /* 每次连接都是一个新的 session */
        sessionUsername.put(session.getId(), username);
        WebSocketData webSocketData = new WebSocketData(username, session);

        if (users.containsKey(username)) {
            close(users.get(username).getSession());
        }

        users.put(username, webSocketData);

        Result<?> result = new Result<>().ok("连接成功");
        sendMessage(session, result);
    }

    public void onMessage(Session sender, String msg) {
        if (!sessionUsername.containsKey(sender.getId())) {
            close(sender);
            return;
        }
        if (StringUtil.isEmpty(msg) || !msg.startsWith("{") || !msg.endsWith("}")) {
            return;
        }
        log.info("OnMessage {} {}", sender.getId(), msg);

        SockedBody requestBody;

        Result<?> result = new Result<>();
        try {
            requestBody = JSON.parseObject(msg, SockedBody.class);
            if (requestBody.getType() == null) {
                requestBody.setType("");
            }
            switch (requestBody.getType()) {
                case "api":
                    result = Objects.requireNonNull(BeanHeaderUtils.getBean(WebSocketRequestServer.class)).run(requestBody.getTo(), requestBody.getMsg());
                    break;
                case "text":
                    chatService.onMessage(sender, requestBody);
                    break;
                default:
                    break;
            }
            result.setId(requestBody.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Result<>().error();
        }
        sendMessage(sender, result);
    }

    /**
     * 向客户端发送消息
     *
     * @param session 客户的session
     * @param result  消息
     */
    public void sendMessage(Session session, Result<?> result) {

        CachedThreadPool.submit(() -> {
            String msg = JSON.toJSONString(result);
            log.info("ws send message -> {}", msg);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.error("send message error，" + e.getMessage(), e);
            }
            if (Constant.UniversalCode.UNAUTHORIZED.getCode() == result.getCode()) {
                close(session);
            }
        });
    }

    /**
     * 因任何情况的关闭
     *
     * @param session .
     */
    public void close(Session session) {

        //客户端断开连接
        String username = sessionUsername.get(session.getId());
        if (Objects.nonNull(username)) {
            users.remove(username);
        }
        sessionUsername.remove(session.getId());
        try {
            session.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
