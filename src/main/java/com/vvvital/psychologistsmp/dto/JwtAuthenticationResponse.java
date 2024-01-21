package com.vvvital.psychologistsmp.dto;

public class JwtAuthenticationResponse {
    public JwtAuthenticationResponse(String token, UserResponseDTO user) {
        this.token = token;
        this.user=user;
    }

    private String token;

    private UserResponseDTO user;

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
