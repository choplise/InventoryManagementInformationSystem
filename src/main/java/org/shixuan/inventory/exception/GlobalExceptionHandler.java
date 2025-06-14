package org.shixuan.inventory.exception;

import org.shixuan.inventory.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result<?> handleServiceException(ServiceException e) {
        LOGGER.error("业务异常: {}", e.getMessage(), e);
        return Result.failed(e.getMessage());
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error("参数验证失败: {}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return Result.validateFailed(message);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        LOGGER.error("参数绑定失败: {}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return Result.validateFailed(message);
    }

    /**
     * 处理未知的异常
     */
    @ExceptionHandler(Throwable.class)
    public Result<?> handleException(Throwable e) {
        LOGGER.error("系统异常: {}", e.getMessage(), e);
        return Result.failed("系统繁忙，请稍后再试");
    }
} 