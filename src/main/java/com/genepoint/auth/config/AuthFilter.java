package com.genepoint.auth.config;

import com.genepoint.auth.domain.Token;
import com.genepoint.auth.domain.TokenStorageStrategy;
import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.Role;
import com.genepoint.auth.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Component
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final TokenStorageStrategy tokenStorage;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (ObjectUtils.isEmpty(tokenHeader)) {
                logger.trace("failed to authentication cause tokenHeader header not found");
                filterChain.doFilter(request, response);
                return;
            }
            Optional<Token> optionalToken = tokenStorage.loadByTokenValue(tokenHeader);
            if (optionalToken.isEmpty()) {
                logger.error("failed to authentication cause invalid token");
                authenticationEntryPoint.commence(request, response, null);
                return;
            }

            Token token = optionalToken.get();

            if (token.isExpired()) {
                logger.error("failed to authentication cause token is expired");
                authenticationEntryPoint.commence(request, response, null);
                return;
            }

            token.refresh();

            User principle = token.getPrinciple();
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : principle.getRoles()) {
                List<String> permissions = role.getPermissions().stream().map(Permission::getName).toList();
                authorities.addAll(AuthorityUtils.createAuthorityList(permissions.toArray(new String[0])));
            }

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principle.getUsername(), principle.getPassword(), authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException failed) {
            authenticationEntryPoint.commence(request, response, failed);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
