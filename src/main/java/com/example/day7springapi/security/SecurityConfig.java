package com.example.day7springapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.core.annotation.Order;


@Configuration
@EnableMethodSecurity

public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter=jwtAuthFilter;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());

        httpSecurity.authorizeHttpRequests(auth ->auth
                .requestMatchers(
                        "/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers("/api/books/**").authenticated()
                .anyRequest().permitAll()
                );
        httpSecurity.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
