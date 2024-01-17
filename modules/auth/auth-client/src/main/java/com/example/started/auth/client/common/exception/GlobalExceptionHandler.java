package com.example.started.auth.client.common.exception;

import com.example.common.v0.constant.Constant;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @since 1.0.0
 */
@Log4j2
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<?> handleMyException(ServerException ex) {
        Result<?> result = new Result<>();
        result.error();
        this.saveLog(ex);
        return Result.error(ex.getMsg());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException ex) {
        log.error(ex);
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result<?> handleException(AccessDeniedException ex) {
        this.saveLog(ex);
        return Result.error(Constant.UniversalCode.UNAUTHORIZED, "此操作无权限");
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        this.saveLog(ex);
        return Result.error("异常情况出现");
    }

    /**
     * todo 保存异常日志
     */
    private void saveLog(Exception ex) {
//        SysLogErrorEntity log = new SysLogErrorEntity();
//
//        //请求相关信息
//        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
//        log.setIp(IpUtils.getIpAddr(request));
//        assert request != null;
//        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
//        log.setRequestUri(request.getRequestURI());
//        log.setRequestMethod(request.getMethod());
//        Map<String, String> params = HttpContextUtils.getParameterMap(request);
//        log.setRequestParams(JSON.toJSONString(params));
//
//        //异常信息
//        log.setErrorInfo(ExceptionUtils.getErrorStackTrace(ex));
//
//        sysLogErrorService.save(log);
    }
}
