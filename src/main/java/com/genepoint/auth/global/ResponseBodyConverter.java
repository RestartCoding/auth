package com.genepoint.auth.global;

/**
 * @author xiabiao
 * @since 2023-09-05
 */
public interface ResponseBodyConverter {

    Class<?> source();

    Object convert(Object s);
}
