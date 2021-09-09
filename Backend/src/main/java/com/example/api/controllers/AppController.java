package com.example.api.controllers;

import com.example.api.util.AbstractResponse;
import com.example.api.util.ResponseBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "General")
public class AppController {
    /**
     * Ping the backend to see if it is running.
     *
     * @return a message confirming the backend is running.
     */
    @GetMapping(value = "/", produces = "application/json")
    @ApiOperation("Return a message to confirm the API is running.")
    public ResponseEntity<AbstractResponse> ping() {
        return new ResponseBuilder<>(HttpStatus.OK).build();
    }
}
