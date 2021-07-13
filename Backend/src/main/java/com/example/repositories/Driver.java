package com.example.repositories;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.db2.jcc.am.SqlIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Driver {
    protected static final Database database = Database.getInstance();
    protected static final Connection connection = database.getConnection();
    protected static final ObjectMapper mapper = new ObjectMapper();

    private static void insertValues(String stmt, List<String> values) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(stmt)) {
            System.out.println(stmt);
            preparedStatement.clearParameters();
            for (int i = 0; i < values.size(); i++) {
                String value = values.get(i);
                System.out.println(value);
                preparedStatement.setString(i + 1, value);
            }
            System.out.println();
            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected == 0) {
                throw new SQLException("No rows affected when executing\n" + stmt);
            }
        } catch (SqlIntegrityConstraintViolationException ignored) {
            System.err.println("[WARN] Data already in table, skipping\n\n");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static ArrayList<String> readCSV(String filename) {
        Set<String> lines = new HashSet<>();
        Scanner scanner = new Scanner(Driver.class.getResourceAsStream(filename));
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        return new ArrayList<>(lines);
    }

    private static void insertFromCSV(String filename, String table, String columnName) {
        ArrayList<String> data = readCSV("/text-files/csv/" + filename + ".csv");
        String statement = "INSERT INTO " + table + " (" + columnName + ") VALUES (?)";
        for (String line : data) {
            insertValues(statement, Collections.singletonList(line));
        }
    }

    private static void insertFromJSON(String filename, String table, String keyName, String valueName) {
        HashMap<String, String> data = readJSON("/text-files/json/" + filename + ".json");
        String statement = "INSERT INTO " + table + " (" + keyName + ", " + valueName + ") VALUES (";
        statement = addPlaceholders(statement, 2) + ")";
        for (Map.Entry<String, String> keyValuePair : data.entrySet()) {
            insertValues(statement, new ArrayList<>(Arrays.asList(keyValuePair.getKey(), keyValuePair.getValue())));
        }
    }

    private static String addPlaceholders(String stmt, int numOfPlaceHolders) {
        String statement = stmt + "?, ".repeat(Math.max(0, numOfPlaceHolders));
        return numOfPlaceHolders > 0 ? statement.substring(0, statement.length() - 2) : "";
    }

    private static HashMap<String, String> readJSON(String filename) {
        StringBuilder lines = new StringBuilder();
        Scanner scanner = new Scanner(Driver.class.getResourceAsStream(filename));
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

    private static void insertFirstNames() {
        insertFromCSV("first-names", "first_name", "first_name");
    }

    private static void insertLastNames() {
        insertFromCSV("last-names", "last_name", "last_name");
    }

    private static void insertColleges() {
        insertFromCSV("colleges", "college", "college");

    }

    private static void insertTeamNames() {
        insertFromCSV("team-names", "team_name", "team_name");

    }

    private static void insertStates() {
        insertFromCSV("usa-states", "team_state", "team_state");
    }

    private static void insertAppTips() {
        insertFromJSON("Tips", "APP_TIP", "title", "content");

    }

    private static void insertViewDescriptions() {
        insertFromJSON("UI Pages", "VIEW_DESCRIPTION", "title", "content");

    }

    private static void insertAttributes() {

    }

    private static void insertArchetypes() {
        insertFromJSON("Archetype Descriptions", "archetype", "archetype_name", "description");
    }

    private static void insertTeamIcons() {

    }

    public static void main(String[] args) {
//        insertArchetypes();
//        insertColleges();
//        insertStates();
//        insertFirstNames();
//        insertLastNames();
//        insertTeamNames();
        insertAppTips();
        insertAttributes();
        insertArchetypes();
        insertViewDescriptions();
        insertTeamIcons();

//        System.out.println("College: " + NameService.randomCollege());
//        System.out.println("First Name: " + NameGenerator.randomFirstName());
//        System.out.println("Last Name: " + NameGenerator.randomLastName());
//        System.out.println("Team Name: " + NameGenerator.randomTeamName());
//        System.out.println("State: " + NameGenerator.randomTeamState());
    }
}
