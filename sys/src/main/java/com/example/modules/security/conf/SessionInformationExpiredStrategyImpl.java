package com.example.modules.security.conf;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 同一账号同时登录的用户数受限的处理
 */
@Log4j2
@Configuration
public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent request) throws IOException, ServletException {
        log.info("同一账号同时登录的用户数受限的处理，访问[{}]失败", request);
    }
}
