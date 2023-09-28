package com.genepoint.auth.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Component
@Primary
public class InMemoryStrategy implements TokenStorageStrategy {

    private final Map<String, Token> token2Principal = new ConcurrentHashMap<>();

    private final Map<String, String> username2token = new ConcurrentHashMap<>();

    @Override
    public void store(Token token) {
        username2token.put(token.getPrinciple().getUsername(), token.getValue());
        token2Principal.put(token.getValue(), token);
    }

    @Override
    public Optional<Token> loadByTokenValue(String tokenValue) {
        return Optional.ofNullable(token2Principal.get(tokenValue));
    }

    @Override
    public Optional<Token> loadByUsername(String username) {
        String tokenValue = username2token.get(username);
        if (tokenValue == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(token2Principal.get(tokenValue));
    }

    @Override
    public void removeByUsername(String username) {
        String tokenValue = username2token.remove(username);
        if (tokenValue != null) {
            token2Principal.remove(tokenValue);
        }
    }

    @Override
    public void removeByTokenValue(String tokenValue) {
        Token token = token2Principal.remove(tokenValue);
        if (token != null) {
            username2token.remove(token.getPrinciple().getUsername());
        }
    }
}
