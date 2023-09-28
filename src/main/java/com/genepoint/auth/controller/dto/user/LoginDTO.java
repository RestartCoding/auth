package com.genepoint.auth.controller.dto.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "username can't be blank")
    @NotEmpty(message = "username can't be empty")
    private String username;

    @NotBlank(message = "password can't be blank")
    @NotEmpty(message = "password can't be empty")
    private String password;
}
