package com.example.started.auth.config;

import com.example.common.v0.constant.Constant;
import com.example.started.auth.client.conf.MixHandler;
import com.example.started.auth.client.filter.JwtTokenCheckFilter;
import com.example.started.auth.modules.user.v1.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //启用Web安全功能
@AllArgsConstructor
public class SecurityConfig {

    private final JwtTokenCheckFilter jwtTokenCheckFilter;
    private final MixHandler myAuthenticationHandler;


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 允许抛出用户不存在的异常
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsServiceImpl myUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(this.pw());
        provider.setUserDetailsPasswordService(myUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public PasswordEncoder pw() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 自定义RememberMe服务token持久化仓库
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //路径配置
        http.authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            // .requestMatchers(HttpMethod.POST, USER.toArray(new String[0])).permitAll()
                            .anyRequest()
                            .authenticated();
                })
                //登陆
                .addFilterAt(jwtTokenCheckFilter, UsernamePasswordJSONAuthenticationFilter.class)
                //登出
                .logout((lo) -> lo.logoutUrl(Constant.User.LOGOUT).logoutSuccessHandler(myAuthenticationHandler))

                //禁用 csrf
                .csrf(AbstractHttpConfigurer::disable)

                //会话管理
                .sessionManagement((session) -> {
                    session.maximumSessions(1).expiredSessionStrategy(myAuthenticationHandler);
                    //引入redis-session依赖后已不再需要手动配置 sessionRegistry
//                .sessionRegistry(new SpringSessionBackedSessionRegistry<>(new RedisIndexedSessionRepository(RedisConfig.createRedisTemplate())))
                    //禁止后登陆挤下线
//               .maxSessionsPreventsLogin(true)
                });

        //rememberMe
//         http.rememberMe().rememberMeServices(rememberMeServices);
        // 权限不足时的处理
        http.exceptionHandling((a) -> a.authenticationEntryPoint(myAuthenticationHandler));
        return http.build();
    }

}
