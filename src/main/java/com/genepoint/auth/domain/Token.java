package com.genepoint.auth.domain;

import com.genepoint.auth.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author xiabiao
 * @since 2023-09-07
 */
@Getter
@Setter
public class Token {

    public static final Duration VALID_DURATION = Duration.of(60, ChronoUnit.MINUTES);

    private String value;

    private Long expireTime;

    private User principle;

    public Token(String value, User principle) {
        this.value = value;
        this.principle = principle;
        this.expireTime = System.currentTimeMillis() + VALID_DURATION.toMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireTime;
    }

    public void refresh() {
        expireTime = System.currentTimeMillis() + VALID_DURATION.toMillis();
    }
}
