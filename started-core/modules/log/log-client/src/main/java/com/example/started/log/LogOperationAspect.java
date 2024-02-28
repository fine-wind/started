package com.example.started.log;

import com.example.started.common.v0.annotation.LogOperation;
import com.example.started.log.dto.enums.OperationStatusEnum;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 操作日志，切面处理类
 */
@Log4j2
@Aspect
@Component
public class LogOperationAspect {
//    @Autowired
//    @Lazy
//    private SysLogOperationService sysLogOperationService;

    @Pointcut("@annotation(com.example.started.common.v0.annotation.LogOperation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            //执行方法
            Object result = point.proceed();

            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.SUCCESS.value());

            return result;
        } catch (Exception e) {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveLog(point, time, OperationStatusEnum.FAIL.value());

            throw e;
        }
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) throws Exception {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        LogOperation annotation = method.getAnnotation(LogOperation.class);

//        SysLogOperationEntity _log = new SysLogOperationEntity();
//        if (annotation != null) {
//            //注解上的描述
//            _log.setOperation(annotation.value());
//        }
//
//        //登录用户信息
//        SecurityUserDetails user = SecurityUser.getUser();
//        if (user != null) {
//            _log.setCreatorName(user.getUsername());
//        }
//
//        _log.setStatus(status);
//        _log.setRequestTime((int) time);
//
//        //请求相关信息
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//        // _log.setIp(IpUtils.getIpAddr(request));
//        _log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        _log.setRequestUri(request.getRequestURI());
//        _log.setRequestMethod(request.getMethod());
//
//        //请求参数
//        Object[] args = joinPoint.getArgs();
//        try {
//            String params = JSON.toJSONString(args[0]);
//            _log.setRequestParams(params);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//
//        //保存到DB
//        sysLogOperationService.save(_log);
    }
}
