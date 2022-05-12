package com.example.common.websocket;

import com.example.common.constant.Constant;
import com.example.common.utils.SpringContextUtils;
import com.example.common.websocket.config.WebSocketConfig;
import com.example.common.websocket.service.WsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

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
