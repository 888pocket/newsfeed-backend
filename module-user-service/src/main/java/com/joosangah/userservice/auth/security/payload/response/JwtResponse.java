package com.joosangah.userservice.auth.security.payload.response;

import lombok.Getter;

@Getter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String id;
    private String username;
    private String email;
    private String role;

    public JwtResponse(String accessToken, String refreshToken, String id, String username,
            String email, String role) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
