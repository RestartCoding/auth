package com.genepoint.auth.service;

/**
 * @author xiabiao
 * @since 2023-10-08
 */
public interface TokenService {

    /**
     * validate if token is valid
     *
     * @param token token
     * @return true if valid otherwise false
     */
    boolean validate(String token);
}
