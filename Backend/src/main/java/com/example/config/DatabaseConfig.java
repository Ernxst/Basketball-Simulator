package com.example.config;

import com.example.api.AppLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setAutoCommit(false);
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        DataSource dataSource = new HikariDataSource(config);
        seed(dataSource.getConnection());
        return dataSource;
    }

    private void seed(Connection connection) {
        AppLogger.log("=== Seeding Database ===");
        DatabaseSeeder seeder = new DatabaseSeeder(connection);
        seeder.seed();
        AppLogger.log("=== Database Seeded ===");
    }

    @Bean
    public Connection connection() throws SQLException {
        return dataSource().getConnection();
    }
}