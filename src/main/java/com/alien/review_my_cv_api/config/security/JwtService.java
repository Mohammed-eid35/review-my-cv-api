package com.alien.review_my_cv_api.config.security;

import com.alien.review_my_cv_api.entity.User;
import com.alien.review_my_cv_api.service.token.TokenBlacklistService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenBlacklistService tokenBlacklistService;

    @Value("${application.security.jwt.secret-key}")
    private String JwtSecretKey;

    @Value("${application.security.jwt.expiration}")
    private long JwtExpirationTime;

    public String generateAccessToken(User user) {
        return generateToken(user);
    }

    public String generateRefreshToken(User user) {
        return generateToken(user);
    }

    private String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails);
    }

    private String buildToken(HashMap<String, Object> claims, UserDetails userDetails) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationTime))
                .claim("authorities", authorities)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token)
                && !tokenBlacklistService.isTokenBlacklisted(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        return Optional
                .ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .filter(authHeader -> authHeader.startsWith(TOKEN_PREFIX))
                .map(authHeader -> authHeader.substring(TOKEN_PREFIX.length()))
                .orElse(null);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
