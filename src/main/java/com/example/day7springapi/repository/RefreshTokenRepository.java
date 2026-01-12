package com.example.day7springapi.repository;

import com.example.day7springapi.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface RefreshTokenRepository     extends JpaRepository<RefreshTokenEntity,Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
}

