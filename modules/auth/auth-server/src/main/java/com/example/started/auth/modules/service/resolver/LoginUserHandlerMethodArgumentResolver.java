package com.example.started.auth.modules.service.resolver;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.annotation.LoginUser;
import com.example.started.auth.modules.user.v1.entity.SysUserEntity;
import com.example.started.auth.modules.user.v1.service.UserServiceV1;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * TODO 有 @LoginUser注解的方法参数，注入当前登录用户
 */
@Component
@AllArgsConstructor
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserServiceV1 userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(Constant.REQUEST.HEADER.TOKEN, RequestAttributes.SCOPE_REQUEST);
        if (!(object instanceof Long)) {
            return null;
        }

        //获取用户信息

        return userService.getUserByUserId(String.valueOf(object));
    }
}
