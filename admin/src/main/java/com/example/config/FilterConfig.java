package com.example.config;

import com.example.common.v0.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.DispatcherType;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {

    // @Bean
    // public FilterRegistrationBean shiroFilterRegistration() {
    //     FilterRegistrationBean registration = new FilterRegistrationBean();
    //     // todo registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
    //     //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
    //     registration.addInitParameter("targetFilterLifecycle", "true");
    //     registration.setEnabled(true);
    //     registration.setOrder(Integer.MAX_VALUE - 1);
    //     registration.addUrlPatterns("/*");
    //     return registration;
    // }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
