package com.example.started.modules.webSocket;

import com.example.started.modules.webSocket.config.WebSocketConfig;
import com.example.started.modules.webSocket.service.WsService;
import com.example.common.v0.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * WebSocket服务
 */
@Slf4j
@Component
@ServerEndpoint(value = Constant.User.WEB_SOCKET, configurator = WebSocketConfig.class)
public class WebSocketServer {

    @Autowired
    private WsService wsService;


    public static int getOnLine() {
        return -123;
    }

    @OnOpen
    public void open(Session session, @PathParam(Constant.REQUEST.HEADER.TOKEN) String token) {
        wsService.open(session, token);
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        wsService.onMessage(session, msg);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        wsService.close(session);
        log.error(throwable.getMessage(), throwable);
    }

    @OnClose
    public void onClose(Session session) {
        wsService.close(session);
        log.debug("websocket close, session id：" + session.getId());
    }


}
