package com.example.started.common.exception;

import com.example.started.common.constant.Constant;
import com.example.started.common.v0.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 通用异常处理器 - 放在 common 模块中
 * 所有微服务都可以复用
 */
@Slf4j
@Order()  // 设置较低优先级，允许子应用覆盖
@RestControllerAdvice
@ConditionalOnWebApplication
@ConditionalOnMissingBean(name = "globalExceptionHandler") // 如果子应用定义了，则不注册
@RequiredArgsConstructor
public class CommonExceptionHandler {

    /**
     * 处理数据库唯一约束异常 - 所有服务通用
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException ex, HttpServletRequest request) {
        log.error("[Common] Duplicate key for {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        return Result.error(Constant.UniversalCode.SERVER_ERROR);
    }

    /**
     * 处理通用异常 - 兜底处理
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex, HttpServletRequest request) {
        log.error("[Common] Unhandled exception for {} {}: {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex);

        return Result.error(Constant.UniversalCode.SERVER_ERROR);
    }

}