package com.genepoint.auth.service;

import com.genepoint.auth.domain.Token;

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

    Token info(String token);
}
