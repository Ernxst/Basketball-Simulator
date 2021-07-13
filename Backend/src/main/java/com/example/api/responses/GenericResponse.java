package com.example.api.responses;

import org.springframework.http.HttpStatus;

public class GenericResponse {
    private String message;
    private HttpStatus code;

    public GenericResponse(String message) {
        super();
        this.message = message;
        this.code = HttpStatus.OK;
    }

    public GenericResponse(String message, HttpStatus code) {
        super();
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}