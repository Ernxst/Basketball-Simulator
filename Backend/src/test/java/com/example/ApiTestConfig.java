package com.example;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConfigurationPropertiesScan("com.example.config")
@ComponentScan({"com.example.services", "com.example.config", "com.example.api"})
@EntityScan("com.example.entities")
//@EnableJpaRepositories("com.example.repositories")
public class ApiTestConfig {
}
