package com.genepoint.auth.controller.dto;

import com.genepoint.auth.controller.dto.token.TokenValidationDTO;
import com.genepoint.auth.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author xiabiao
 * @since 2023-10-08
 */
@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {

    private TokenService tokenService;

    @PostMapping("/validate")
    public ApiResponse<Object> validate(@RequestBody @Valid TokenValidationDTO dto) {
        boolean valid = tokenService.validate(dto.getToken());
        return ApiResponse.ok(Map.of("valid", valid));
    }

    @GetMapping("/info")
    public ApiResponse<Object> info(String token) {
        return ApiResponse.ok(tokenService.info(token));
    }
}
