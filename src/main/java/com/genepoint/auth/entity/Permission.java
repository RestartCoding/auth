package com.genepoint.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xiabiao
 * @since 2023-08-26
 */
@Getter
@Setter
@Entity
@ToString
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(insertable = false, updatable = false)
    private Date createTime;

    @Column(insertable = false)
    private Date updateTime;

}
