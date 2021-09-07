package com.example.api.controllers.account.responses;

import com.example.api.util.AbstractResponse;


public class AuthSuccessResponse extends AbstractResponse {
    private final String username;
    private final String token;

    public AuthSuccessResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
