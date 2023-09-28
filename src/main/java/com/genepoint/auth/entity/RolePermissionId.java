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
public class RolePermissionId implements Serializable {

    private Integer roleId;

    private Integer permissionId;
}
