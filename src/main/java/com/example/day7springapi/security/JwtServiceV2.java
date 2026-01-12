package com.example.day7springapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtServiceV2 {
    private final SecretKey key;
    private final long accessExpMs;
    private final long refreshExpMs;

    public JwtServiceV2(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-expiration-seconds}") long accessSeconds,
            @Value("${app.jwt.refresh-expiration-seconds}") long  refreshSeconds
    ){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpMs = accessSeconds*1000;
        this.refreshExpMs = refreshSeconds*1000;
    }

    public String generateAccessToken(String username , String role){
        Date now = new Date();
        Date exp = new Date(now.getTime()+accessExpMs);

        return Jwts.builder()
                .subject(username)
                .claim("role",role)
                .claim("type","access")
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username){
        Date now = new Date();
        Date exp = new Date(now.getTime() + refreshExpMs);

        return Jwts.builder()
                .subject(username)
                .claim("type","refresh")
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token){
        Object role = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role");
        return role == null ? null  : role.toString();
    }

    public String extractType(String token) {
        Object type = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("type");
        return type == null ? null : type.toString();
    }
    public Instant extractExpiration(String token){
        Date exp = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return exp.toInstant();
    }
}
