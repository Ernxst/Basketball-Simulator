package com.example.entities.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordRequest {
    @NotNull
    private String username;
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

    public ChangePasswordRequest(@NotNull String username, @NotNull String currentPassword, @NotNull String newPassword) {
        this.username = username;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public ChangePasswordRequest() {
    }
}
