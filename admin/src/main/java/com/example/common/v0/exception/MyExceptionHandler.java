package com.example.common.v0.exception;

import com.alibaba.fastjson.JSON;
import com.example.common.v0.constant.Constant;
import com.example.common.v0.utils.HttpContextUtils;
import com.example.common.v0.utils.IpUtils;
import com.example.common.v0.utils.Result;
import com.example.modules.log.entity.SysLogErrorEntity;
import com.example.modules.log.service.SysLogErrorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


/**
 * 全局异常处理器
 *
 * @since 1.0.0
 */
@Log4j2
@RestControllerAdvice
public class MyExceptionHandler {

    private final SysLogErrorService sysLogErrorService;

    public MyExceptionHandler(SysLogErrorService sysLogErrorService) {
        this.sysLogErrorService = sysLogErrorService;
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<?> handleMyException(ServerException ex) {
        Result<?> result = new Result<>();
        result.error(ex.getCode(), ex.getMsg());
        saveLog(ex);
        return result;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        Result<?> result = new Result<>();
        result.error(UniversalCode.DB_RECORD_EXISTS, ex.getMessage());
        log.error(ex);
        return result;
    }

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        Result<Object> objectResult = new Result<>().error();
        if (ex instanceof AccessDeniedException) {
            objectResult.error(Constant.UniversalCode.UNAUTHORIZED, ex.getMessage());
        }
        saveLog(ex);

        return objectResult;
    }

    /**
     * 保存异常日志
     */
    private void saveLog(Exception ex) {
        SysLogErrorEntity log = new SysLogErrorEntity();

        //请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));
        assert request != null;
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());
        Map<String, String> params = HttpContextUtils.getParameterMap(request);
        if (Objects.nonNull(params)) {
            log.setRequestParams(JSON.toJSONString(params));
        }

        //异常信息
        log.setErrorInfo(ExceptionUtils.getErrorStackTrace(ex));

        //保存
        sysLogErrorService.save(log);
    }
}
