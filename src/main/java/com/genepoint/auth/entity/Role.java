package com.genepoint.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author xiabiao
 * @since 2023-08-26
 */

@Getter
@Setter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roleName;

    @Column(insertable = false, updatable = false)
    private Date createTime;

    @Column(insertable = false)
    private Date updateTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
    joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "permissionId", referencedColumnName = "id")})
    private List<Permission> permissions;
}