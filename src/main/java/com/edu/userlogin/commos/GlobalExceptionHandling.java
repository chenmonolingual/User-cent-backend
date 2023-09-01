package com.edu.userlogin.commos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author qinyan
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception exception){
        log.error(exception.getMessage());
        return Result.error(ErrorCode.SERVER_CODE,exception.getMessage());
    }
}
