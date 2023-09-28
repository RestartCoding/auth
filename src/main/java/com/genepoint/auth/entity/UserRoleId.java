package com.genepoint.auth.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

    private Integer userId;

    private Integer roleId;
}
