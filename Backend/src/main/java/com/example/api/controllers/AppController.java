package com.example.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    /**
     * Ping the backend to see if it is running.
     *
     * @return a message confirming the backend is running.
     */
    @GetMapping("/")
    public String ping() {
        return "Hello";
    }
}
