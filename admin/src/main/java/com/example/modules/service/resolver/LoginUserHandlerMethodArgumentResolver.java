package com.example.modules.service.resolver;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.data.annotation.LoginUser;
import com.example.modules.sys.user.v1.dto.UserDto;
import com.example.modules.sys.user.v1.entity.SysUserEntity;
import com.example.modules.sys.user.v1.service.UserServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
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
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    @Lazy
    private UserServiceV1 userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(Constant.REQUEST.HEADER.TOKEN, RequestAttributes.SCOPE_REQUEST);
        if (!(object instanceof Long)) {
            return null;
        }

        //获取用户信息
        UserDto user = userService.getUserByUserId(String.valueOf(object));

        return null;
    }
}
