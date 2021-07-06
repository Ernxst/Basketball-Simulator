package com.example;

/*
TODO - Pass "b" flag from docker/python run script to here
 */

import com.example.api.AppLogger;
import com.example.db.Database;
import org.apache.commons.cli.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Driver code to start RESTful API.
 */
@ConfigurationPropertiesScan({"com.example.config"})
@EnableAutoConfiguration
@ComponentScan({"com.example.services", "com.example.config", "com.example.api"})
@EntityScan("com.example.entities")
@EnableJpaRepositories("com.example.repositories")
public class Application implements ApplicationRunner {
    // The port this backend runs on.
    public static String PORT;
    // The port the frontend runs on.
    public static String APP_PORT = "8080";
    /**
     * Whether to broadcast device IP address.
     */
    public static boolean broadcast;

    public static void main(String[] args) {
        // Load properties file
        Properties properties = new Properties();
        try (InputStream is = Application.class.getResourceAsStream("../../application.properties")) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String defaultPort = properties.getProperty("backend.app.port");

        Options options = new Options();
        Option portOption = new Option("p", "port", true,
                "(optional) choose to run on a desired port - default is " + defaultPort);
        portOption.setRequired(false);
        options.addOption(portOption);

        Option logOption = new Option("l", false, "(optional) include logging information during execution");
        logOption.setRequired(false);
        options.addOption(logOption);

        Option broadcastOption = new Option("b", false, "(optional) broadcast IP address of this device to allow other devices to connect to the backend automatically. DO NOT USE ON PUBLIC NETWORKS!");
        logOption.setRequired(false);
        options.addOption(broadcastOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String port = cmd.getOptionValue("port", defaultPort);
        List<String> arguments = Arrays.asList(cmd.getArgs());
        AppLogger.LOG_API_CALLS = arguments.contains("l");

        Application.broadcast = arguments.contains("b");
        AppLogger.welcome();
        SpringApplication application = new SpringApplication(Application.class);

        try {
            // Just to ensure the port is a valid port.
            int ignored = Integer.parseInt(port);
            PORT = port;
            application.setDefaultProperties(Collections.singletonMap("server.port", port));
        } catch (NumberFormatException ignored) {
            System.out.println("error: invalid port \"" + port + "\" - must be a number");
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }
        application.run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        // Connect to cloud database.
        Database database = Database.getInstance();
        // Output startup logging information
        AppLogger.start();
    }
}
