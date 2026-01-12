package com.example.day7springapi.controller;

import com.example.day7springapi.dto.*;
import com.example.day7springapi.service.AuthServiceV2;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authV2")
public class AuthControllerV2 {
    private final AuthServiceV2 service;
    public  AuthControllerV2 (AuthServiceV2 service){
        this.service = service;
    }

    @PostMapping("/register")
    public String registerv2(@Valid @RequestBody RegisterRequest request){
        service.registerv2(request);
        return "Đăng Ký";
    }

    @PostMapping("login")
    public AuthTokensResponse loginv2(@Valid @RequestBody LoginRequest request){
        return service.loginv2(request);
    }

    @PostMapping("/refresh")
    public AuthTokensResponse refreshv2(@RequestBody RefreshRequest request){
        return service.refreshv2(request);
    }

    @PostMapping("/logout")
    public String logoutv2(@RequestBody LogoutRequest request){
        return service.logoutv2(request);
    }
}
