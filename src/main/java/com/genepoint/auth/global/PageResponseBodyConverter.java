package com.genepoint.auth.global;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
@Component
public class PageResponseBodyConverter implements ResponseBodyConverter {

    @Override
    public Class<?> source() {
        return Page.class;
    }

    @Override
    public Object convert(Object s) {
        Page page = (Page) s;
        com.genepoint.auth.controller.dto.Page<?> res = new com.genepoint.auth.controller.dto.Page<>();
        res.setPageNum(page.getNumber());
        res.setPageSize(page.getSize());
        List content = page.getContent();
        res.setContents(content);
        res.setTotal(page.getTotalElements());
        res.setPages(page.getTotalPages());
        return res;
    }
}
