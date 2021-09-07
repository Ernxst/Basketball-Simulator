package com.example.api.util;

public class GenericResponse extends AbstractResponse {
    private String message;

    public GenericResponse(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}