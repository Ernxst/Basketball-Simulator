package com.example.db.interfaces.freeAgent;

import com.example.db.interfaces.player.NewPlayerInterface;
import com.example.entities.player.FreeAgent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FreeAgentInterface extends NewPlayerInterface {
    private static final String insertFreeAgentStmt = "INSERT INTO team (team_state, team_name, relocations, renames, is_user_team," +
            "date_founded, iconID, leagueID) VALUES (?,?,?,?,?,?,?,?)";

    /**
     * Insert a new free agent into the given league.
     *
     * @param freeAgent the free agent to retrieve data from.
     * @param leagueID  the ID of the league the free agent belongs to.
     * @return the generated ID of the free agent.
     */
    public static int insertFreeAgent(FreeAgent freeAgent, int leagueID) {
        int playerID = -1;
        try (PreparedStatement preparedInsertStmt = connection.prepareStatement(insertFreeAgentStmt, Statement.RETURN_GENERATED_KEYS)) {
            insertFreeAgentData(freeAgent, leagueID, preparedInsertStmt);

            int affectedRows = preparedInsertStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating free agent failed, no rows affected.");
            }
            connection.commit();

            try (ResultSet generatedPlayerIDs = preparedInsertStmt.getGeneratedKeys()) {
                if (generatedPlayerIDs.next()) {
                    playerID = generatedPlayerIDs.getInt(1);
                } else {
                    throw new SQLException("Creating free agent failed, no ID obtained.");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return playerID;
    }

    /**
     * Insert free agent data into the SQL statement.
     *
     * @param freeAgent         the free agent object to retrieve data from.
     * @param leagueID          the ID of the league the free agent belongs to.
     * @param preparedStatement the statement to insert data into.
     * @throws SQLException if data could not be inserted.
     */
    // TODO - Finish insertFreeAgentData()
    private static void insertFreeAgentData(FreeAgent freeAgent, int leagueID, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.clearParameters();
    }
}
