package com.example.config;


import com.example.api.AppLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class DatabaseSeeder {
    private final Connection connection;
    private final ObjectMapper mapper = new ObjectMapper();

    public DatabaseSeeder(Connection connection) {
        this.connection = connection;
    }

    private void createTables() {
        AppLogger.log("=== Creating Tables ===");
        try {
            String contents = readFile("Postgres-Schema.sql");
            String[] commands = contents.split(";");
            for (String command : commands) {
                insertValues(command.strip() + ";", new ArrayList<>(), true);
            }
            AppLogger.log("=== Tables Created ===");
        } catch (NullPointerException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String readFile(String filename) throws IOException, URISyntaxException {
        // Use when running outside container
//        URL resource = getClass().getResource(filename);
//        Path path = Paths.get(resource.toURI());
        Path path = Path.of(filename);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private void insertValues(String stmt, List<String> values) {
        insertValues(stmt, values, false);
    }

    private void insertValues(String stmt, List<String> values, boolean create) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(stmt)) {
//            AppLogger.log(stmt);
            preparedStatement.clearParameters();
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
//                AppLogger.log("  - " + value);
                preparedStatement.setString(i + 1, value);
            }
//            System.out.println();
            int rowsAffected = preparedStatement.executeUpdate();
            if (!create && rowsAffected == 0) {
                throw new SQLException("No rows affected when executing\n" + stmt);
            }
            connection.commit();

        } catch (SQLException exception) {
            String state = exception.getSQLState();
            Throwable cause = exception.getCause();
            if (state.equals("23505") || (cause != null && cause.getMessage().contains("duplicate key"))) {
//                AppLogger.log("Duplicate item encountered, skipping\n");
            } else {
                exception.printStackTrace();
            }
        }
    }

    private ArrayList<String> readCSV(String filename) {
        Set<String> lines = new HashSet<>();
        Scanner scanner = new Scanner(DatabaseSeeder.class.getResourceAsStream(filename));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return new ArrayList<>(lines);
    }

    private void insertFromCSV(String filename, String table, String columnName) {
        ArrayList<String> data = readCSV("/text-files/csv/" + filename + ".csv");
        String statement = "INSERT INTO " + table + " (" + columnName + ") VALUES (?)";
        for (String line : data) {
            insertValues(statement, Collections.singletonList(line));
        }
    }

    private void insertFromJSON(String filename, String table, String keyName, String valueName) {
        HashMap<String, String> data = readJSON("/text-files/json/" + filename + ".json");
        String statement = "INSERT INTO " + table + " (" + keyName + ", " + valueName + ") VALUES (";
        statement = addPlaceholders(statement, 2) + ")";
        for (Map.Entry<String, String> keyValuePair : data.entrySet()) {
            insertValues(statement, new ArrayList<>(Arrays.asList(keyValuePair.getKey(), keyValuePair.getValue())));
        }
    }

    private String addPlaceholders(String stmt, int numOfPlaceHolders) {
        String statement = stmt + "?, ".repeat(Math.max(0, numOfPlaceHolders));
        return numOfPlaceHolders > 0 ? statement.substring(0, statement.length() - 2) : "";
    }

    private HashMap<String, String> readJSON(String filename) {
        StringBuilder lines = new StringBuilder();
        Scanner scanner = new Scanner(DatabaseSeeder.class.getResourceAsStream(filename));
        while (scanner.hasNextLine()) {
            lines.append(scanner.nextLine());
        }
        scanner.close();
        try {
            return mapper.readValue(lines.toString(), HashMap.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private void insertFirstNames() {
        insertFromCSV("first-names", "first_name", "first_name");
    }

    private void insertLastNames() {
        insertFromCSV("last-names", "last_name", "last_name");
    }

    private void insertColleges() {
        insertFromCSV("colleges", "college", "college");
    }

    private void insertTeamNames() {
        insertFromCSV("team-names", "team_name", "team_name");
    }

    private void insertStates() {
        insertFromCSV("usa-states", "team_state", "team_state");
    }

    private void insertAppTips() {
        insertFromJSON("Tips", "APP_TIP", "title", "content");
    }

    private void insertViewDescriptions() {
        insertFromJSON("UI Pages", "VIEW_DESCRIPTION", "title", "content");
    }

    private void insertAttributes() {

    }

    private void insertArchetypes() {
        insertFromJSON("Archetype Descriptions", "archetype", "archetype_name", "description");
    }

    private void insertTeamIcons() {

    }

    public void seed() {
        createTables();
        AppLogger.log("=== Inserting Default Data ===");
        AppLogger.log("=== Inserting Archetypes ===");
        insertArchetypes();
        AppLogger.log("=== Inserting Colleges ===");
        insertColleges();
        AppLogger.log("=== Inserting US States ===");
        insertStates();
        AppLogger.log("=== Inserting First Names ===");
        insertFirstNames();
        AppLogger.log("=== Inserting Last Names ===");
        insertLastNames();
        AppLogger.log("=== Inserting Team Names ===");
        insertTeamNames();
        AppLogger.log("=== Inserting App Tips ===");
        insertAppTips();
        AppLogger.log("=== Inserting Attributes ===");
        insertAttributes();
        AppLogger.log("=== Inserting View Descriptions ===");
        insertViewDescriptions();
        AppLogger.log("=== Inserting Team Icons ===");
        insertTeamIcons();
        AppLogger.log("=== Default Data Inserted ===");
    }
}
