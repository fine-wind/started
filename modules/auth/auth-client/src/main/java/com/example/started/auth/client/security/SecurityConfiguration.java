package com.example.started.auth.client.security;

import com.example.common.v0.utils.SpringContextUtils;
import com.example.started.auth.client.conf.MixHandler;
import com.example.started.auth.client.filter.JwtTokenCheckFilter;
import com.example.started.auth.client.conf.WhiteListConf;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.LinkedList;
import java.util.List;

@Lazy
@Configuration("com.example.started.auth.security.SecurityConfiguration")
@EnableWebSecurity  //启用Web安全功能
@EnableMethodSecurity()
@AllArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenCheckFilter jwtTokenCheckFilter;
    private final MixHandler mixHandler;


    @Bean("security.auth.client")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final List<String> whiteList = new LinkedList<>();
        SpringContextUtils.getBeansOfType(WhiteListConf.class).forEach((k, v) -> whiteList.addAll(List.of(v.whiteList())));
        //登陆
        http.addFilterAt(jwtTokenCheckFilter, LogoutFilter.class);
        //路径配置
        http.authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers(whiteList.toArray(new String[0])).permitAll()
                            .anyRequest().authenticated()
                    ;
                })
                //禁用 csrf
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((e) -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        ;
        // http.formLogin((a) -> {
        //     a.loginProcessingUrl(Constant.User.LOGIN);
        // });

        //rememberMe
//         http.rememberMe().rememberMeServices(rememberMeServices);

        // 权限不足时的处理
        http.exceptionHandling((a) -> a.authenticationEntryPoint(mixHandler));
        return http.build();
    }

}
