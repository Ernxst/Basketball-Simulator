package com.example.config;

import com.example.api.AppLogger;
import com.example.entities.user.User;
import com.example.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    // TODO: Store secret in environment variable
    private static final String JWT_SECRET = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private static final String JWT_ISSUER = "com.example";
    private static final int TOKEN_LIFE = 7 * 24 * 60 * 60 * 1000; // 1 week from issue

    public String generateToken(User user) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer(JWT_ISSUER)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + TOKEN_LIFE))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUsername(String token) {
        return decodeToken(token).getSubject();
    }

    public Date getExpirationDate(String token) {
        return decodeToken(token).getExpiration();
    }

    private Claims decodeToken(String token) {
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