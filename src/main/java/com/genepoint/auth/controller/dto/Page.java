package com.genepoint.auth.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Getter
@Setter
public class Page<E> {

    private Collection<E> contents;

    private long total;

    private int pageNum;

    private int pageSize;

    private int pages;
}
