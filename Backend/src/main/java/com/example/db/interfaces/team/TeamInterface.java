package com.example.db.interfaces.team;

import com.example.app.util.Util;
import com.example.db.interfaces.AbstractInterface;
import com.example.db.interfaces.player.PlayerInterface;
import com.example.entities.player.Player;
import com.example.entities.team.Team;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamInterface extends AbstractInterface {
    private static final String insertTeamStmt = "INSERT INTO TEAM (TEAM_STATE, TEAM_NAME, RELOCATIONS, RENAMES, IS_USER_TEAM," +
            "DATE_FOUNDED, ICON_ID, LEAGUE_ID) VALUES (?,?,?,?,?,?,?,?)";
    private static final String selectIconIDsStmt = "SELECT ICON_ID FROM TEAM_ICON";
    private static final String selectTeamByIdStmt = "SELECT * FROM TEAM WHERE TEAM_ID = ?";
    private static final String selectPlayersInTeamStmt = "SELECT PLAYER_ID FROM PLAYER WHERE TEAM_ID = ?";

    private static final List<Integer> iconIDs;

    static {
        iconIDs = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectIconIDsStmt)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                iconIDs.add(resultSet.getInt(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Insert a new team into the database and return their generated teamID.
     *
     * @param team     the team to insert.
     * @param leagueID the ID of the league the team belongs to.
     * @return the generated teamID of the team.
     */
    public static int insertTeam(Team team, int leagueID) {
        int teamID = -1;
        try (PreparedStatement preparedInsertStmt = connection.prepareStatement(insertTeamStmt, Statement.RETURN_GENERATED_KEYS)) {
            insertTeamData(team, leagueID, preparedInsertStmt);

            int affectedRows = preparedInsertStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating team failed, no rows affected.");
            }
            connection.commit();

            try (ResultSet generatedTeamIDs = preparedInsertStmt.getGeneratedKeys()) {
                if (generatedTeamIDs.next()) {
                    teamID = generatedTeamIDs.getInt(1);
                } else {
                    throw new SQLException("Creating team failed, no ID obtained.");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return teamID;
    }

    /**
     * Insert team data into the SQL statement.
     *
     * @param team              the team object to retrieve data from.
     * @param leagueID          the ID of the league the team belongs to.
     * @param preparedStatement the statement to insert data into.
     * @throws SQLException if data could not be inserted.
     */
    private static void insertTeamData(Team team, int leagueID, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.clearParameters();
        preparedStatement.setString(1, team.getState());
        preparedStatement.setString(2, team.getName());
        preparedStatement.setInt(3, team.getRelocations());
        preparedStatement.setInt(4, team.getRenames());
        preparedStatement.setBoolean(5, team.isUserTeam());
        preparedStatement.setDate(6, Date.valueOf(team.getDateFounded()));
        preparedStatement.setInt(7, team.getIconID());
        preparedStatement.setInt(8, leagueID);
    }

    /**
     * Return a random team icon ID.
     *
     * @return a random team icon ID.
     */
    public static int randomTeamIconID() {
        return Util.randomChoice(iconIDs);
    }

    /**
     * Return the team specified by the ID.
     *
     * @param teamID the ID of the team.
     * @return an object representing the team.
     */
    public static Team getTeamByID(int teamID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectTeamByIdStmt)) {
            preparedStatement.clearParameters();
            preparedStatement.setInt(1, teamID);
            ResultSet resultSet = preparedStatement.executeQuery();

            String state = resultSet.getString(2);
            String name = resultSet.getString(3);
            int relocations = resultSet.getInt(4);
            int renames = resultSet.getInt(5);
            boolean is_user_team = resultSet.getBoolean(6);
            Date date = resultSet.getDate(7);
            LocalDate dateFounded = date.toLocalDate();
            int iconID = resultSet.getInt(8);

            Map<Integer, Player> players = getPlayersInTeam(teamID);
            return new Team(state, name, relocations, renames, is_user_team, dateFounded, iconID, players);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * Return a map of all players in the given team.
     *
     * @param teamID the ID of the team.
     * @return the players in that team.
     */
    public static Map<Integer, Player> getPlayersInTeam(int teamID) {
        Map<Integer, Player> players = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectPlayersInTeamStmt)) {
            preparedStatement.clearParameters();
            preparedStatement.setInt(1, teamID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int playerID = resultSet.getInt(1);
                Player player = PlayerInterface.getPlayerByID(playerID);
                players.put(playerID, player);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return players;
    }
}
