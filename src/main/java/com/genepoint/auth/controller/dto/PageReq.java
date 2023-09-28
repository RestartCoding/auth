package com.genepoint.auth.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiabiao
 * @since 2023-08-31
 */
@Getter
@Setter
public class PageReq {

    private int pageNum = 1;

    private int pageSize = 10;
}
