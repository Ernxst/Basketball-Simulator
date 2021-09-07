package com.example.config;

import com.example.api.AppLogger;
import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    // TODO: Store secret and environment variable
    private static final String JWT_SECRET = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private static final String JWT_ISSUER = "com.example";
    private static final int TOKEN_LIFE = 7 * 24 * 60 * 60 * 1000; // 1 week from issue

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(format("%s", user.getUsername()))
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFE))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String token, UserRepository userRepository) {
        try {
            String username = getUsername(token);
            return userRepository.findByUsername(username).isPresent() && !isTokenExpired(token);
        } catch (IllegalArgumentException ex) {
            AppLogger.log("JWT claims string is empty - " + ex.getMessage());
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }
}