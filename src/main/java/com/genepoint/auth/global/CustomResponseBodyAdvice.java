package com.genepoint.auth.global;

import com.genepoint.auth.controller.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@ControllerAdvice
@AllArgsConstructor
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private List<ResponseBodyConverter> responseBodyConverters;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body == null){
            return null;
        }

        if (ApiResponse.class == body.getClass()) {
            ApiResponse<Object> apiResponse = (ApiResponse) body;
            Object data = apiResponse.getData();
            if (data == null){
                return body;
            }
            for (ResponseBodyConverter converter : responseBodyConverters) {
                if (converter.source().isAssignableFrom(data.getClass())) {
                    Object convert = converter.convert(data);
                    return ApiResponse.of(apiResponse.getCode(), apiResponse.getMessage(), convert);
                }
            }
        }

        return body;
    }
}
