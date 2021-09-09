package com.example.api.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder<T extends AbstractResponse> {
    private final ResponseEntity.BodyBuilder builder;
    private T body;
    private String jwtToken;

    public ResponseBuilder(HttpStatus status) {
        // TODO: Add all necessary headers
        builder = ResponseEntity.status(status.value())
                .contentType(MediaType.APPLICATION_JSON);
    }

    public ResponseBuilder(HttpStatus status, T body) {
        this(status);
        this.body = body;
    }

    public ResponseBuilder(HttpStatus status, String jwtToken) {
        this(status);
        this.jwtToken = jwtToken;
    }

    public ResponseBuilder(HttpStatus status, T body, String jwtToken) {
        this(status);
        this.body = body;
        this.jwtToken = jwtToken;
    }

    public void addHeader(String name, String value) {
        builder.header(name, value);
    }

    public void addHeader(HttpHeaders headers) {
        builder.headers(headers);
    }

    public ResponseEntity<T> build() {
        if (jwtToken != null)
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        if (body != null)
            return builder.body(body);
        return builder.build();
    }
}
