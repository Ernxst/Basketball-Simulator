package com.example.api.responses;

public class UserLoginResponse {
    private final String username;
    private final String accessToken;

    public UserLoginResponse(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
