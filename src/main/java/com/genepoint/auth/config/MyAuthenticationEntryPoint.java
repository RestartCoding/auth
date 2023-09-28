package com.genepoint.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genepoint.auth.controller.dto.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Component
@AllArgsConstructor
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper om;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponse<Void> resp = ApiResponse.of(String.valueOf(HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED.getReasonPhrase(), null);
        response.getWriter().write(om.writeValueAsString(resp));
    }
}
