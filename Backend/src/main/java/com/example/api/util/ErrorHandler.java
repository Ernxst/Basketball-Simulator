package com.example.api.util;

import com.example.services.user.UsernameTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleException(UsernameNotFoundException e) {
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GenericErrorResponse> handleException(BadCredentialsException e) {
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<GenericErrorResponse> handleException(UsernameTakenException e) {
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.CONFLICT, body).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GenericErrorResponse> handleException(RuntimeException e) {
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.INTERNAL_SERVER_ERROR, body).build();
    }
}
