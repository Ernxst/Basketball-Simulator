package com.example.db;

import com.example.db.interfaces.AbstractInterface;
import com.example.db.interfaces.NameGenerator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Driver extends AbstractInterface {

    private static void insertSingleValue(String stmt, String value) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(stmt)) {
            preparedStatement.clearParameters();
            preparedStatement.setString(1, value);
            System.out.println(stmt);
            System.out.println(value);
            System.out.println();
            int rowsAffected = preparedStatement.executeUpdate();
            connection.commit();
            if (rowsAffected == 0) {
                throw new SQLException("No rows affected when executing\n" + stmt);
            }
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
        for (String line : data) {
            String statement = "INSERT INTO " + table + " (" + columnName + ") VALUES (?)";
            insertSingleValue(statement, line);
        }
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

    }

    private static void insertViewDescriptions() {

    }

    private static void insertAttributes() {

    }

    private static void insertArchetypes() {

    }

    private static void insertTeamIcons() {

    }

    public static void main(String[] args) {
        insertArchetypes();
        insertColleges();
        insertStates();
        insertFirstNames();
        insertLastNames();
        insertTeamNames();
        insertAppTips();
        insertAttributes();
        insertViewDescriptions();
        insertTeamIcons();

        System.out.println("College: " + NameGenerator.randomCollege());
        System.out.println("First Name: " + NameGenerator.randomFirstName());
        System.out.println("Last Name: " + NameGenerator.randomLastName());
        System.out.println("Team Name: " + NameGenerator.randomTeamName());
        System.out.println("State: " + NameGenerator.randomTeamState());
    }
}
