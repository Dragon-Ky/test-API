package com.example.day7springapi.service;

import com.example.day7springapi.dto.*;
import com.example.day7springapi.entity.UserEntity;
import com.example.day7springapi.exception.BadRequestException;
import com.example.day7springapi.repository.UserRepository;
import com.example.day7springapi.security.JwtServiceV2;
import com.example.day7springapi.security.Role;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceV2 {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceV2 jwtServiceV2;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceV2(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtServiceV2 jwtServiceV2,
            AuthenticationManager authenticationManager,
            RefreshTokenService refreshTokenService
    ){
        this.userRepository=userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServiceV2 = jwtServiceV2;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    public void registerv2(RegisterRequest request){
        String username = request.getUsername().trim();
        if (userRepository.existsByUsername(username)) throw  new BadRequestException("User đã tồn tại");

        String hash = passwordEncoder.encode(request.getPassword());
        userRepository.save(new UserEntity(username,hash,Role.USER));
    }

    public AuthTokensResponse loginv2(LoginRequest loginRequestv2){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestv2.getUsername(),loginRequestv2.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UserEntity userEntity = userRepository.findByUsername(loginRequestv2.getUsername())
                .orElseThrow(()->new BadRequestException("Invalid credentials"));
        String access = jwtServiceV2.generateAccessToken(userEntity.getUsername(),userEntity.getRole().name());
        String refresh = refreshTokenService.create(userEntity).getToken();

        return new AuthTokensResponse(access,refresh,userEntity.getRole().name());

    }

    public AuthTokensResponse refreshv2(RefreshRequest refreshRequest){
        var rt = refreshTokenService.verify(refreshRequest.getRefreshToken());
        UserEntity user = rt.getUser();

        String access = jwtServiceV2.generateAccessToken(user.getUsername(),user.getRole().name());

        refreshTokenService.revoke(rt.getToken());
        String newRefresh = refreshTokenService.create(user).getToken();

        return new AuthTokensResponse(access,newRefresh,user.getRole().name());
    }

    public String logoutv2(LogoutRequest logoutRequest){
        refreshTokenService.revoke(logoutRequest.getRefreshToken());
        return "Đăng xuất";
    }
}
