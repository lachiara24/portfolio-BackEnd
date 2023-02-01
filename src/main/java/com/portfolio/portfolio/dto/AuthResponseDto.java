package com.portfolio.portfolio.dto;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
