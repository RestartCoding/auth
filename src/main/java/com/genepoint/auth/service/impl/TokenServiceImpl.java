package com.genepoint.auth.service.impl;

import com.genepoint.auth.domain.Token;
import com.genepoint.auth.domain.TokenStorageStrategy;
import com.genepoint.auth.exception.BusinessException;
import com.genepoint.auth.service.TokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xiabiao
 * @since 2023-10-08
 */
@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);

    private TokenStorageStrategy tokenStorageStrategy;

    @Override
    public boolean validate(String token) {
        Optional<Token> optionalToken = tokenStorageStrategy.loadByTokenValue(token);
        if (optionalToken.isEmpty()) {
            log.debug("token [{}] is invalid cause not found", token);
            return false;
        }
        Token storeToken = optionalToken.get();
        if (storeToken.isExpired()) {
            log.debug("token [{}] is invalid cause expired", token);
            return false;
        }
        return true;
    }

    @Override
    public Token info(String token) {
        Optional<Token> optionalToken = tokenStorageStrategy.loadByTokenValue(token);
        return optionalToken.orElse(null);
    }
}
