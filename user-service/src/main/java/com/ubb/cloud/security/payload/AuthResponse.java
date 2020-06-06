package com.ubb.cloud.security.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private List<String> roles;
    private Integer userId;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthResponse(String accessToken, List<String> roles, Integer userId) {
        this.accessToken = accessToken;
        this.roles = roles;
        this.userId = userId;
    }

}