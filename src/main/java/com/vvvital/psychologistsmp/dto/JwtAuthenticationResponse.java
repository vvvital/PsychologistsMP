package com.vvvital.psychologistsmp.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationResponse {
    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
