package com.genepoint.auth.controller.dto.token;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiabiao
 * @since 2023-10-08
 */
@Getter
@Setter
public class TokenValidationDTO {

    @NotEmpty(message = "token can't be empty")
    @NotBlank(message = "token can't be blank")
    private String token;
}