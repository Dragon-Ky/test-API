package com.example.day7springapi.security;

import com.example.day7springapi.entity.UserEntity;
import com.example.day7springapi.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository){
        this.repository =repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity userEntity = repository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Không tìm thấy user"));

        return new  org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPasswordHash(),
                List.of(new SimpleGrantedAuthority("ROLE_"+userEntity.getRole().name()))
        );
    }
}
