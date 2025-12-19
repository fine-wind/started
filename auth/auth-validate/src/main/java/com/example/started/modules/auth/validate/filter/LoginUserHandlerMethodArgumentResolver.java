package com.example.started.modules.auth.validate.filter;

import com.example.started.modules.auth.validate.annotation.*;
import com.example.started.modules.auth.validate.dto.TokenUserId;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 */
@Component
@AllArgsConstructor
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(TokenUserId.class) && parameter.hasParameterAnnotation(LoginUserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        //获取用户ID
//        Object object = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
//        if (object == null) {
//            return null;
//        }

        //获取用户信息
//        UserEntity user = userService.getUserByUserId((Long) object);
        String principal = String.valueOf(context.getAuthentication().getPrincipal());
        return new TokenUserId(principal);
    }
}
