package com.user.service.util;

import com.user.service.enums.UserRoleEnum;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expirationMinutes}")
    private long expirationMinutes;


    public String generateToken(Long userId, String username, UserRoleEnum role) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes*60);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("username",username)
                .claim("role", role.name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

    }

}
