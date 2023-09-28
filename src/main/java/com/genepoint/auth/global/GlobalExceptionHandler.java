package com.genepoint.auth.global;

import com.genepoint.auth.controller.dto.ApiResponse;
import com.genepoint.auth.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String COMMON_ERR_MESSAGE = "System Error";

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Object> handle(BusinessException e) {
        return ApiResponse.err(e.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handle(Exception e) {
        return ApiResponse.err(COMMON_ERR_MESSAGE, null);
    }
}
