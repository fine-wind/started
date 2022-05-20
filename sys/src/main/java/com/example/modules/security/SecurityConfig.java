package com.example.modules.security;

import com.example.common.constant.Constant;
import com.example.modules.security.conf.*;
import com.example.modules.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
//@EnableWebSecurity  //启用Web安全功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    final LoginSuccessHandler loginSuccessHandler;
    final SysAuthenticationEntryPoint sysAuthenticationEntryPoint;
    final LoginUserAccessDeniedHandler loginUserAccessDeniedHandler;


    private final UserDetailsServiceImpl userDetailsService;

    private final FailureHandler failureHandler;
    private final SysLogoutSuccessHandler logoutSuccessHandler;
    private final JwtTokenCheckFilter jwtTokenCheckFilter;

    @Autowired
    public SecurityConfig(LoginSuccessHandler loginSuccessHandler, SysAuthenticationEntryPoint sysAuthenticationEntryPoint,
                          LoginUserAccessDeniedHandler loginUserAccessDeniedHandler,
                          UserDetailsServiceImpl userDetailsService,
                          FailureHandler failureHandler,
                          SysLogoutSuccessHandler logoutSuccessHandler, JwtTokenCheckFilter jwtTokenCheckFilter) {
        this.loginSuccessHandler = loginSuccessHandler;

        this.sysAuthenticationEntryPoint = sysAuthenticationEntryPoint;
        this.loginUserAccessDeniedHandler = loginUserAccessDeniedHandler;
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.jwtTokenCheckFilter = jwtTokenCheckFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 这里使用了前后端分离的模式
        //

        http.exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
        });
        http.cors().configurationSource(corsConfigurationSource());
//        http.cors().disable();
        /*禁用csrf跨站请求攻击*/
        http.csrf().disable();

        http.authorizeRequests()
                // 设置请求报错403 请求被拒绝
                .and().exceptionHandling()
                .authenticationEntryPoint(sysAuthenticationEntryPoint)
//                .accessDeniedHandler(loginUserAccessDeniedHandler)
                // 登出
                .and()
                .logout()
                .logoutUrl(Constant.User.LOGOUT)
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()
        // 会话管理
//                .expiredSessionStrategy(sessionInformationExpiredStrategy)
//                .and()
//                .sessionAuthenticationFailureHandler(failureHandler)
        ;
        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenCheckFilter, UsernamePasswordJSONAuthenticationFilter.class);
        http.authorizeRequests().and().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .disable();
        // 超时处理
//                .invalidSessionStrategy(invalidSessionStrategy)
        //同一账号同时登录最大用户数
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    public UsernamePasswordJSONAuthenticationFilter customAuthenticationFilter() throws Exception {
        UsernamePasswordJSONAuthenticationFilter filter = new UsernamePasswordJSONAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setFilterProcessesUrl(Constant.User.LOGIN);

        //这句很关键，重用 WebSecurityConfigurerAdapter 配置的 AuthenticationManager ，不然要自己组装AuthenticantionManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> strings = new ArrayList<>(3);
        for (String host : Constant.PARAM_CONF.APP_SETTINGS_CONF.THIS_HOST.getValue().split(",")) {
            String trim = host.trim();
            String http = "http://";
            String https = "https://";
            if (trim.startsWith(http) || trim.startsWith(https))
                strings.add(trim);
            else {
                strings.add(http + trim);
                strings.add(https + trim);
            }
        }
        configuration.setAllowedOrigins(strings);
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.POST.name(),
                HttpMethod.GET.name(), HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name(), HttpMethod.DELETE.name()));
//        configuration.setAllowedMethods(Collections.singletonList("*"));
        // configuration.addExposedHeader("Location"); // 暴露 Location header
        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders
        configuration.setAllowedHeaders(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * todo 放行一些静态资源 检查这些是否需要删除
     *
     * @param web .
     * @throws Exception .
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        /* 不拦截的接口*/
        web.ignoring().antMatchers(
                Constant.User.JOIN
        );

        /* 页面不拦截*/
        web.ignoring().mvcMatchers("/**/*.html");

        /*静态资源不拦截*/
        web.ignoring().mvcMatchers(
                "/**/*.js",
                "/**/*.css",
                "/img/**",
                "/static/**",
                "/favicon.ico"
        );
    }
}
