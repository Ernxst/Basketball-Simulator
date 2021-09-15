package com.example.config;

import com.example.api.AppLogger;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private String dbUrl;
    private String username;
    private String password;
    private DataSource dataSourceObj = null;
    private String jdbcUrl;

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Bean
    public DataSource dataSource() throws URISyntaxException, SQLException {
        if (dataSourceObj == null) {
            dbUrl = System.getenv("SPRING_DATASOURCE_URL");
            username = System.getenv("POSTGRES_USER");
            password = System.getenv("POSTGRES_PASSWORD");
            AppLogger.log("Creating new data source");
            dataSourceObj = createDataSource();
            seed();
        }
        return dataSourceObj;
    }

    private DataSource createDataSource() throws URISyntaxException {
        HikariConfig config = new HikariConfig();
        jdbcUrl = getJdbcURL();
        config.setJdbcUrl(jdbcUrl);
        config.setAutoCommit(true);
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    /**
     * Convert Heroku Postgres URL (postgres://[username]:[password]@[host]:[port]/[database] into JDBC format.
     *
     * @return the URL converted into JDBC format.
     * @throws URISyntaxException
     */
    private String getJdbcURL() throws URISyntaxException {
        String prefix = "jdbc:postgresql://";
        // Already in JDBC format
        if (dbUrl.startsWith(prefix))
            return dbUrl;
        URI dbUri = new URI(dbUrl);
        String host = dbUri.getHost();
        String port = String.valueOf(dbUri.getPort());
        String[] splitUrl = dbUrl.split("/");
        String dbName = splitUrl[splitUrl.length - 1];
        username = dbUri.getUserInfo().split(":")[0];
        password = dbUri.getUserInfo().split(":")[1];
        return prefix + host + ":" + port + "/" + dbName;
    }

    /**
     * Create tables and insert default data into the database.
     */
    private void seed() throws SQLException, URISyntaxException {
        AppLogger.log("=== Seeding Database ===");
        DatabaseSeeder seeder = new DatabaseSeeder(connection());
        seeder.seed();
        AppLogger.log("=== Database Seeded ===");
    }

    @Bean
    public Connection connection() throws SQLException, URISyntaxException {
        return dataSource().getConnection();
    }
}