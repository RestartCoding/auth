package com.genepoint.auth.servlet;

import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xiabiao
 * @since 2023-09-27
 */
@WebFilter(urlPatterns = "/*")
public class RequestIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put("requestId", UUID.randomUUID().toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
