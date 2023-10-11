package com.genepoint.auth.controller.dto.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @author xiabiao
 * @since 2023-10-10
 */
@Getter
@Setter
@ToString
public class TokenInfoDTO {

    @NotEmpty(message = "token can't be empty")
    @NotBlank(message = "token can't be blank")
    private String token;
}
