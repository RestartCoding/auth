package com.genepoint.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole {

    @Id
    private Integer userId;

    @Id
    private Integer roleId;
}
