package com.example.started.modules.websocket.config;

import com.example.started.common.v0.utils.SpringContextUtils;
import com.example.started.modules.auth.validate.config.JwtService;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.Objects;

/**
 * WebSocket配置
 */
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        String queryString = request.getQueryString();
        if (Objects.nonNull(queryString) && queryString.startsWith("token=")) {
            queryString = queryString.replace("token=", "");
        }
        JwtService jwtService = SpringContextUtils.getBean(JwtService.class);
        String userIdFromToken = jwtService.getUserIdFromToken(queryString);
        sec.getUserProperties().put("userId", String.valueOf(userIdFromToken));
    }

}
