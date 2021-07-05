package com.example.db.interfaces.league;

import com.example.db.interfaces.AbstractInterface;
import com.example.entities.league.League;

import java.sql.*;

public class LeagueInterface extends AbstractInterface {
    private static final String insertLeagueStmt = "INSERT INTO LEAGUE (USERNAME, NAME, START_DATE) VALUES (?, ?, ?)";
    // TODO - Finish insertLeague()

    /**
     * Insert a new league into the database.
     *
     * @param username the user the league belongs to.
     * @param league   the league to retrieve data from.
     * @return the generated ID of the league.
     */
    public static int insertLeague(String username, League league) {
        int leagueID = insertLeagueIntoDB(username, league);
        insertLeagueSeason(leagueID, league);
        insertLeagueRecords(leagueID, league);
        return leagueID;
    }

    private static int insertLeagueIntoDB(String username, League league) {
        int leagueID = 0;
        try (PreparedStatement preparedInsertStmt = connection.prepareStatement(insertLeagueStmt, Statement.RETURN_GENERATED_KEYS)) {
            insertLeagueData(username, league, preparedInsertStmt);

            int affectedRows = preparedInsertStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating league failed, no rows affected.");
            }
            connection.commit();
            try (ResultSet generatedLeagueIDs = preparedInsertStmt.getGeneratedKeys()) {
                if (generatedLeagueIDs.next()) {
                    leagueID = generatedLeagueIDs.getInt(1);
                } else {
                    throw new SQLException("Creating league failed, no ID obtained.");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return leagueID;
    }

    private static void insertLeagueData(String username, League league,
                                         PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.clearParameters();
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, league.getName());
        preparedStatement.setDate(3, Date.valueOf(league.getStartDate()));
    }

    // TODO - Implement insertLeagueSeason()

    /**
     * @param leagueID the ID of the league being inserted.
     * @param league   the league object.
     */
    public static void insertLeagueSeason(int leagueID, League league) {

    }

    // TODO - Implement insertLeagueRecords()

    /**
     * @param leagueID the ID of the league being inserted.
     * @param league   the league object.
     */
    public static void insertLeagueRecords(int leagueID, League league) {

    }

    // TODO - Implement getLeagueByID()
    public static League getLeagueByID(int leagueID) {
        return null;
    }
}
