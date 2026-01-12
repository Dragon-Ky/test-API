package com.example.day7springapi.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = false,length = 300)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    @ManyToOne(optional = false)
    private UserEntity user;

    public RefreshTokenEntity(){}

    public RefreshTokenEntity(String token,Instant expiresAt,UserEntity user){
        this.token=token;
        this.expiresAt=expiresAt;
        this.user=user;
        this.revoked =false;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
