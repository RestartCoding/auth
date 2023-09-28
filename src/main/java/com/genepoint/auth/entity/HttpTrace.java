package com.genepoint.auth.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xiabiao
 * @since 2023-09-06
 */
@Entity
@Getter
@Setter
public class HttpTrace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private Date beginTime;

    private Date endTime;

    private Long timeTaken;

    @Column(insertable = false, updatable = false)
    private Date createTime;
}
