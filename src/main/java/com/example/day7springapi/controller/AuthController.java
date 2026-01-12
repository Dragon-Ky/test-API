package com.example.day7springapi.controller;

import com.example.day7springapi.dto.*;
import com.example.day7springapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public  AuthController (AuthService service){
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request){
        service.register(request);
        return "Đăng Ký";
    }

    @PostMapping("login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request){
        return service.login(request);
    }
}
