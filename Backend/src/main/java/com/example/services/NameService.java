package com.example.services;

import com.example.app.util.Util;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class NameService {
    private final Connection connection;

    private final List<String> teamNames;
    private final List<String> teamStates;
    private final List<String> firstNames;
    private final List<String> lastNames;
    private final List<String> colleges;

    public NameService(Connection connection) {
        this.connection = connection;
        teamNames = getElements("team_name");
        teamStates = getElements("team_state");
        firstNames = getElements("first_name");
        lastNames = getElements("last_name");
        colleges = getElements("college");
    }

    public String randomTeamName() {
        return Util.randomChoice(teamNames);
    }

    public String randomTeamState() {
        return Util.randomChoice(teamStates);
    }

    public String randomFirstName() {
        return Util.randomChoice(firstNames);
    }

    public String randomLastName() {
        return Util.randomChoice(lastNames);
    }

    public String randomCollege() {
        return Util.randomChoice(colleges);
    }

    private List<String> getElements(String table) {
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
}
