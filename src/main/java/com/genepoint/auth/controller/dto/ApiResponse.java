package com.genepoint.auth.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Getter
@Setter
public class ApiResponse<E> {

    public static final String OK = "0";

    public static final String ERR = "-1";

    private String code;

    private String message;

    private E data;

    private ApiResponse(String code, String message, E data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return of(OK, message, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(OK, "", data);
    }

    public static <T> ApiResponse<T> err(String message, T data) {
        return of(ERR, message, data);
    }

    public static <T> ApiResponse<T> of(String code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}