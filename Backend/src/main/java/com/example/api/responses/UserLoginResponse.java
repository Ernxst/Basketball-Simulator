package com.example.api.responses;

public class UserLoginResponse extends GenericResponse {
    private final String username;
    private final String accessToken;

    public UserLoginResponse(String username, String accessToken) {
        super("Login success");
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
