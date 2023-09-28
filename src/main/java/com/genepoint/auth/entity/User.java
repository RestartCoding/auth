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
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Column(insertable = false, updatable = false)
    private Date createTime;

    @Column(insertable = false)
    private Date updateTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")})
    @Column(insertable = false)
    private List<Role> roles;
}