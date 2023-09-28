package com.genepoint.auth.controller.dto.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Getter
@Setter
public class CreateUserDTO {

    @NotBlank(message = "username can't be blank")
    @NotEmpty(message = "username can't be empty")
    @Length(max = 16, message = "username is too long")
    private String username;

    @NotBlank(message = "username can't be blank")
    @NotEmpty(message = "username can't be empty")
    @Length(max = 64, message = "password is too long")
    private String password;

    private List<Integer> roleIds;
}