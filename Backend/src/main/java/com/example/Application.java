package com.example;

import com.example.api.AppLogger;
import com.example.repositories.Database;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Driver code to start RESTful API.
 */
@SpringBootApplication
public class Application {
    // The port this backend runs on.
    @Value("${server.port}")
    public static String PORT;
    // The port the frontend runs on.
    public static final String APP_PORT = "8080";

    public static void main(String[] args) {
        // Connect to cloud database.
        Database database = Database.getInstance();
        // Output startup logging information
        AppLogger.start();
        SpringApplication.run(Application.class, args);
    }
}
