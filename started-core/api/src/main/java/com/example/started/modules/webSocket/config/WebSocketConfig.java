package com.example.started.modules.webSocket.config;

import com.example.common.v0.constant.Constant;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WebSocket 配置
 */
@Configuration
public class WebSocketConfig extends Configurator {

    String regex = Constant.REQUEST.HEADER.TOKEN + "=.{32}";
    Pattern p = Pattern.compile(regex);

    /**
     * 在接通 socket 时，写入用户id
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        List<String> strings = request.getHeaders().get(Constant.REQUEST.HEADER.COOKIE);

        if (Objects.isNull(strings) || strings.isEmpty()) {
            return;
        }
        String cookies = strings.getFirst();
        Matcher m = p.matcher(cookies);
        if (m.find()) {
            String group = m.group();
            if (Objects.nonNull(group)) {
                String[] split = group.split("=");
                if (split.length > 1) {
                    String token = split[1];
                    // todo xing socket
//                    TokenService singleton = TokenServiceImpl.getSingleton();
//
//                    TokenEntity byToken = singleton.getByToken(token);
//                    if (Objects.nonNull(byToken)) {
//                        sec.getUserProperties().put(Constant.USER_KEY, byToken.getUserId());
//                    }
                }
            }
        }

    }

}
