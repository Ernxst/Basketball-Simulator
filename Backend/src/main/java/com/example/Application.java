// TODO: Convert request fields to camelCase and response fields to snake_case
package com.example;

import com.example.api.AppLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

/**
 * Driver code to start RESTful API.
 */
@SpringBootApplication
public class Application {
    // The port this backend runs on.
    public static String PORT;
    // The port the frontend runs on.
    public static final String APP_PORT = "8080";

    private static String getPort() throws IOException {
        String envPort = System.getenv("PORT");
        if (envPort != null)
            return envPort;
        Properties properties = new Properties();
        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream("application.properties");
        properties.load(inputStream);
        return properties.getProperty("server.port");
    }

    public static void main(String[] args) throws IOException {
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("Inside Shutdown Hook")));
        PORT = getPort();
        AppLogger.log("Backend API Started");
        SpringApplication app = new SpringApplication(Application.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", PORT));
        app.run(args);
        AppLogger.log("Listening on 0.0.0.0:" + PORT);
    }
}
