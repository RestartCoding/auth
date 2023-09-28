package com.genepoint.auth.domain;

import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
public interface TokenStorageStrategy {

    /**
     * store token and user
     *
     * @param token token
     */
    void store(Token token);

    /**
     * load user by token
     *
     * @param tokenValue token value
     * @return user
     */
    Optional<Token> loadByTokenValue(String tokenValue);

    Optional<Token> loadByUsername(String username);

    void removeByUsername(String username);

    void removeByTokenValue(String token);
}
