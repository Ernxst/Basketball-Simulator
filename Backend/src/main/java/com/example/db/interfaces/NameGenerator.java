package com.example.db.interfaces;

import com.example.app.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates random names from Db data.
 */
public class NameGenerator extends AbstractInterface {
    private static final List<String> teamNames;
    private static final List<String> teamStates;
    private static final List<String> firstNames;
    private static final List<String> lastNames;
    private static final List<String> colleges;

    static {
        teamNames = getElements("team_name");
        teamStates = getElements("team_state");
        firstNames = getElements("first_name");
        lastNames = getElements("last_name");
        colleges = getElements("college");
    }

    public static String randomTeamName() {
        return Util.randomChoice(teamNames);
    }

    public static String randomTeamState() {
        return Util.randomChoice(teamStates);
    }

    public static String randomFirstName() {
        return Util.randomChoice(firstNames);
    }

    public static String randomLastName() {
        return Util.randomChoice(lastNames);
    }

    public static String randomCollege() {
        return Util.randomChoice(colleges);
    }

    private static List<String> getElements(String table) {
        List<String> elements = new ArrayList<>();
        String stmt = "SELECT * FROM " + table;
        try (PreparedStatement preparedStatement = connection.prepareStatement(stmt)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                elements.add(resultSet.getString(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return elements;
    }

    public static List<String> getTeamNames() {
        return teamNames;
    }

    public static List<String> getTeamStates() {
        return teamStates;
    }

    public static List<String> getFirstNames() {
        return firstNames;
    }

    public static List<String> getLastNames() {
        return lastNames;
    }

    public static List<String> getColleges() {
        return colleges;
    }
}
