package com.example.day7springapi.service;

import com.example.day7springapi.entity.RefreshTokenEntity;
import com.example.day7springapi.entity.UserEntity;
import com.example.day7springapi.exception.BadRequestException;
import com.example.day7springapi.repository.RefreshTokenRepository;
import com.example.day7springapi.security.JwtServiceV2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtServiceV2 jwtServiceV2;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository , JwtServiceV2 jwtServiceV2){
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtServiceV2 = jwtServiceV2;
    }
    public RefreshTokenEntity create(UserEntity user){
        String token = jwtServiceV2.generateRefreshToken(user.getUsername());
        Instant exp = jwtServiceV2.extractExpiration(token);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(token,exp,user);
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public RefreshTokenEntity verify(String refreshToken){
        RefreshTokenEntity refreshTokenEntity= refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(()-> new BadRequestException("Invalid refresh token"));

        if (refreshTokenEntity.isRevoked())throw  new BadRequestException("Refresh token revoked");
        if (refreshTokenEntity.getExpiresAt().isBefore(Instant.now())) throw new BadRequestException("Refresh token expired");

        String type = jwtServiceV2.extractType(refreshToken);
        if (!"refresh".equals(type)) throw  new BadRequestException("Token type is not refresh");

        return refreshTokenEntity;
    }
    public void revoke(String refreshToken){
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(()-> new BadRequestException("Invalid refresh token"));

        refreshTokenEntity.setRevoked(true);
        refreshTokenRepository.save(refreshTokenEntity);
    }
}
