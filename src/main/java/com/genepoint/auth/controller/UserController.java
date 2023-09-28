package com.genepoint.auth.controller;

import com.genepoint.auth.controller.dto.ApiResponse;
import com.genepoint.auth.controller.dto.user.CreateUserDTO;
import com.genepoint.auth.controller.dto.user.LoginDTO;
import com.genepoint.auth.controller.dto.user.UserPageQueryDTO;
import com.genepoint.auth.domain.Token;
import com.genepoint.auth.entity.User;
import com.genepoint.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @PreAuthorize("hasAuthority('user:read') or hasPermission()")
    @GetMapping("/page")
    public ApiResponse<Page<User>> page(UserPageQueryDTO req) {
        Page<User> page = userService.page(req);
        return ApiResponse.ok(page);
    }

    @GetMapping("/info")
    public ApiResponse<User> info(@RequestParam("id") int userId) {
        User user = userService.info(userId);
        return ApiResponse.ok(user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable("id") int id) {
        userService.removeById(id);
        return ApiResponse.ok(null);
    }

    @PostMapping
    public ApiResponse<Object> create(@RequestBody @Valid CreateUserDTO createUserDTO) {
        User user = userService.create(createUserDTO);
        return ApiResponse.ok(Map.of("id", user.getId()));
    }

    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody @Valid LoginDTO dto) {
        log.info("user try to login");
        Token token = userService.login(dto);
        return ApiResponse.ok(token);
    }

    @PostMapping("/logout")
    public ApiResponse<Object> logout() {
        userService.logout();
        return ApiResponse.ok(null);
    }
}