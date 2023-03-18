package com.example.modules.security;

import com.example.common.v0.constant.Constant;
import com.example.modules.security.conf.UsernamePasswordJSONAuthenticationFilter;
import com.example.modules.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity  //启用Web安全功能
public class SecurityConfiguration {

    /**
     * 接口文档放行
     */
    public static final List<String> DOC_WHITE_LIST = List.of("/doc.html");
    public static final List<String> GET_PATH = List.of("/", /*验证码放行*/"/sys/verifyCode/**");
    public static final List<String> USER = List.of(Constant.User.JOIN, Constant.User.LOGIN);


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
     * @param myUserDetailsService myUserDetailsService
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsServiceImpl myUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(aa());
        provider.setUserDetailsPasswordService(myUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public PasswordEncoder aa() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * 自定义RememberMe服务token持久化仓库
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UsernamePasswordJSONAuthenticationFilter loginFilter, MyAuthenticationHandler authenticationHandler) throws Exception {
        //路径配置
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, USER.toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.GET, DOC_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers(HttpMethod.GET, GET_PATH.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
        ;

        //登陆
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        //配置自定义登陆流程后需要关闭 否则可以使用原有登陆方式

        //登出
        http.logout().logoutUrl(Constant.User.LOGOUT).logoutSuccessHandler(authenticationHandler);

        //禁用 csrf
        http.csrf().disable();

        //csrf验证 存储到Cookie中
//        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
//        ;

        //会话管理
        http.sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(authenticationHandler)
        //引入redis-session依赖后已不再需要手动配置 sessionRegistry
//                .sessionRegistry(new SpringSessionBackedSessionRegistry<>(new RedisIndexedSessionRepository(RedisConfig.createRedisTemplate())))
        //禁止后登陆挤下线
//               .maxSessionsPreventsLogin(true)
        ;

        //rememberMe
//         http.rememberMe().rememberMeServices(rememberMeServices);

        // 权限不足时的处理
        http.exceptionHandling().accessDeniedHandler(authenticationHandler).authenticationEntryPoint(authenticationHandler);
        return http.build();
    }

}
