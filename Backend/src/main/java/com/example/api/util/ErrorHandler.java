package com.example.api.util;

import com.example.api.AppLogger;
import com.example.services.user.UsernameTakenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
        GenericErrorResponse body = new GenericErrorResponse("Your username or password was incorrect, please try again.");
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

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<GenericErrorResponse> handleException(SignatureException e) {
        AppLogger.log("Invalid JWT signature - " + e.getMessage());
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<GenericErrorResponse> handleException(MalformedJwtException e) {
        AppLogger.log("Invalid JWT token - " + e.getMessage());
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GenericErrorResponse> handleException(ExpiredJwtException e) {
        AppLogger.log("Expired JWT token - " + e.getMessage());
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<GenericErrorResponse> handleException(UnsupportedJwtException e) {
        AppLogger.log("Unsupported JWT token - " + e.getMessage());
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericErrorResponse> handleException(IllegalArgumentException e) {
        GenericErrorResponse body = new GenericErrorResponse(e.getMessage());
        return new ResponseBuilder<>(HttpStatus.UNAUTHORIZED, body).build();
    }
}
