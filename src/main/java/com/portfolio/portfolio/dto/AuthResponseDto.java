package com.portfolio.portfolio.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    private String userName;
    private Collection<? extends GrantedAuthority> authorities;

    private int id;

    public AuthResponseDto(String accessToken, String userName, Collection<? extends GrantedAuthority> authorities, int id) {
        this.accessToken = accessToken;
        this.userName = userName;
        this.authorities = authorities;
        this.id = id;
    }
}
