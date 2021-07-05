package com.example.api.responses;

public class RegistrationSuccessResponse extends GenericResponse {
    private final String username;

    public RegistrationSuccessResponse(String username) {
        super("Registration success");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
