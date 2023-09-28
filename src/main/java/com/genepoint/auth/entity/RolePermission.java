package com.genepoint.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@IdClass(RolePermissionId.class)
@Entity
@Getter
@Setter
public class RolePermission {

    @Id
    private Integer roleId;

    @Id
    private Integer permissionId;
}
