//package com.example.modules.security.conf;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
///**
// * 同一账号同时登录的用户数受限的处理
// */
//@Log4j2
//@Configuration
//public class BasicAuthenticationFilterImpl extends BasicAuthenticationFilter {
//    /**
//     * Creates an instance which will authenticate against the supplied
//     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
//     * allowing the request to proceed down the filter chain.
//     *
//     * @param authenticationManager the bean to submit authentication requests to
//     */
//    public BasicAuthenticationFilterImpl(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    /**
//     * Creates an instance which will authenticate against the supplied
//     * {@code AuthenticationManager} and use the supplied {@code AuthenticationEntryPoint}
//     * to handle authentication failures.
//     *
//     * @param authenticationManager    the bean to submit authentication requests to
//     * @param authenticationEntryPoint will be invoked when authentication fails.
//     *                                 Typically an instance of {@link BasicAuthenticationEntryPoint}.
//     */
//    public BasicAuthenticationFilterImpl(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//}
