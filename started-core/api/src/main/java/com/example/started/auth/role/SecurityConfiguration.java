package com.example.started.auth.role;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.SpringContextUtils;
import com.example.started.auth.LoginSuccessHandler;
import com.example.started.auth.UsernamePasswordJSONAuthenticationFilter;
import com.example.started.config.MixHandler;
import com.example.started.config.WhiteListConf;
import com.example.started.filter.JwtTokenCheckFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Lazy
@Configuration("com.example.started.auth.security.SecurityConfiguration")
@EnableWebSecurity  //启用Web安全功能
@EnableMethodSecurity()
@AllArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenCheckFilter jwtTokenCheckFilter;
    private final MixHandler mixHandler;
    private final LoginSuccessHandler loginSuccessHandler;


    @Bean("security.auth.client")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        final List<String> whiteList = new LinkedList<>();
        SpringContextUtils.getBeansOfType(WhiteListConf.class).forEach((k, v) -> whiteList.addAll(List.of(v.whiteList())));
        //登陆
        http.addFilterAt(jwtTokenCheckFilter, LogoutFilter.class);
        //路径配置
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(whiteList.toArray(new String[0])).permitAll().anyRequest().authenticated())
                //登陆
                .addFilterAt(jwtTokenCheckFilter, UsernamePasswordJSONAuthenticationFilter.class).formLogin((e) -> {
                    e.loginProcessingUrl(Constant.User.LOGIN);
                    e.successHandler(loginSuccessHandler);
                    e.failureHandler(mixHandler);
                })
//                .rememberMe().rememberMeServices(rememberMeServices)  //rememberMe
                .logout((lo) -> lo.logoutUrl(Constant.User.LOGOUT).logoutSuccessHandler(mixHandler))//登出
                .csrf(AbstractHttpConfigurer::disable) //禁用 csrf
                .cors().disable().sessionManagement((e) -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 权限不足时的处理
        // http.exceptionHandling((a) -> a.authenticationEntryPoint(mixHandler));
        return http.build();
    }

    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authenticationConfiguration .
     * @return .
     * @throws Exception .
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
