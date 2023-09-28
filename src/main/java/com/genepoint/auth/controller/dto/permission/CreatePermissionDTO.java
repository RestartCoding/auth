package com.genepoint.auth.controller.dto.permission;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@Getter
@Setter
@ToString
public class CreatePermissionDTO {

    @NotBlank(message = "name can't be blank")
    @NotEmpty(message = "name can't be empty")
    @Length(max = 64, message = "name is too long")
    private String name;
}
