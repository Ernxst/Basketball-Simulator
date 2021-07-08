package com.example.entities.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public AuthRequest(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public AuthRequest() {

    }
}
