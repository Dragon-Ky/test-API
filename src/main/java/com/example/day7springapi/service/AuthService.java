package com.example.day7springapi.service;

import com.example.day7springapi.dto.*;
import com.example.day7springapi.entity.UserEntity;
import com.example.day7springapi.exception.BadRequestException;
import com.example.day7springapi.repository.UserRepository;
import com.example.day7springapi.security.JwtService;
import com.example.day7springapi.security.Role;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,PasswordEncoder passwordEncoder,JwtService jwtService,AuthenticationManager authenticationManager){
        this.repository= repository ;
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest request){
        String username = request.getUsername().trim();
        if (repository.existsByUsername(username)){
            throw new BadRequestException("Tên tài khoản đã tồn tại");
        }
        String hash =passwordEncoder.encode(request.getPassword());
        UserEntity user = new UserEntity(username,hash,Role.USER);
        repository.save(user);
    }

    public AuthResponse login(LoginRequest request){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        authenticationManager.authenticate(token);

        UserEntity user = repository.findByUsername(request.getUsername())
                .orElseThrow(()-> new BadRequestException("Thông tin đăng nhập không hợp lệ"));

        String jwt = jwtService.generateTonken(user.getUsername(),user.getRole().name());
        return new AuthResponse(jwt,user.getRole().name());
    }
}
