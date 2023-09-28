package com.genepoint.auth.controller.dto.role;

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
public class CreateRoleDTO {

    @NotBlank(message = "roleName can't be blank")
    @NotEmpty(message = "roleName can't be empty")
    @Length(max = 128, message = "roleName is too long")
    private String roleName;

    private List<Integer> permissionIds;
}
