package com.example.api.util;

public class GenericErrorResponse extends AbstractResponse {
    private String error;

    public GenericErrorResponse(String message) {
        super();
        this.error = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}