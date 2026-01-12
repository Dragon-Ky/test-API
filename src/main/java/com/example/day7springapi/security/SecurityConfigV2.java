package com.example.day7springapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@EnableMethodSecurity

public class SecurityConfigV2 {
    private final  JwtAuthFilter jwtAuthFilter;
    public SecurityConfigV2(JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainV2(HttpSecurity http) throws Exception{
        http.securityMatcher("/authV2/**");

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(cors ->cors.configurationSource(corsConfigurationSourceV2()));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/authV2/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-ui.html"
                ).permitAll()
                .requestMatchers("/api/books/**").authenticated()
                .anyRequest().permitAll()
        );
        http.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSourceV2(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET","POST","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
