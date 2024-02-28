//package com.example.util.aop;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import com.example.common.enums.CookieEnum;
//import com.example.common.enums.ResultEnum;
//import com.example.common.exceptions.MyException;
//import com.example.common.utils.CookieUtils;
//
//import jakarta.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
///**
// *
// */
//@Aspect
//@Component
//public class InvoiceAop {
//
//    private static final Logger LOG = LoggerFactory.getLogger(InvoiceAop.class);
//
//    @Pointcut("execution(public * com.example.controller.*.*( ..))")
//    public void pointCut() {
//        LOG.debug("init InvoiceAop");
//    }
//
//    /**
//     * 方法执行之前
//     *
//     * @param joinPoint 连接点
//     */
//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//
//        String userId = CookieUtils.getCookieValue(request, CookieEnum.UserID);
//        if (jump(userId)) {
//            throw new MyException(ResultEnum.USER_EXCEPTIONS);
//        }
//        LOG.debug("Calling method start: {}，params: {} ", joinPoint.getSignature().toString(), Arrays.toString(joinPoint.getArgs()));
//    }
//
//    /**
//     * 是否要跳转
//     *
//     * @param userId 用户id
//     * @return 是否要跳转
//     */
//    private boolean jump(String userId) {
//        /*跳转*/
//        boolean jump = false;
//        return jump;
//    }
//
//}
