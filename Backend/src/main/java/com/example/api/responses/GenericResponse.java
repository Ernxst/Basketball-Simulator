package com.example.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;

public class GenericResponse {
    private String message;
    private HttpStatus code;
    private String error;

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

    public GenericResponse(String message, HttpStatus code, String error) {
        this.message = message;
        this.code = code;
        this.error = error;
    }

    public GenericResponse(List<FieldError> fieldErrors, List<ObjectError> globalErrors) {
        super();
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.message = mapper.writeValueAsString(fieldErrors);
            this.error = mapper.writeValueAsString(globalErrors);
        } catch (JsonProcessingException e) {
            this.message = "";
            this.error += e.getMessage();
        }
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}