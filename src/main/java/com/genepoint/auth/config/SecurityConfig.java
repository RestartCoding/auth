package com.genepoint.auth.config;

import com.genepoint.auth.entity.Permission;
import com.genepoint.auth.entity.User;
import com.genepoint.auth.repository.UserRepository;
import com.genepoint.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final AuthFilter authFilter;

    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Value("${auth.ignore-path}") String ignorePath) throws Exception {

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(ignorePath.split(",")).permitAll()
                .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserService userService, UserRepository userRepository) {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
                String username = token.getName();
                String password = token.getCredentials().toString();
                Optional<User> optionalUser = userRepository.findByUsername(username);
                if (optionalUser.isEmpty()) {
                    log.error("failed to login cause username [{}] not found", username);
                    throw new UsernameNotFoundException("Incorrect username or password");
                }
                if (!optionalUser.get().getPassword().equals(password)) {
                    log.error("failed to login cause bad credentials");
                    throw new BadCredentialsException("Incorrect username or password");
                }
                List<Permission> permissions = userService.listPermission(optionalUser.get().getId());
                List<String> list = permissions.stream().map(Permission::getName).toList();
                List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(list.toArray(new String[0]));
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), authorityList);
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication == UsernamePasswordAuthenticationToken.class;
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}