package com.alien.review_my_cv_api.service.token;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    // TODO: Use Redis to store blacklisted tokens

    private final Set<String> blacklistedTokens = new HashSet<>();

    @Override
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
