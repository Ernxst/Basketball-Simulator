package com.example.db.interfaces.player;

import com.example.db.interfaces.AbstractInterface;
import com.example.entities.player.Player;
import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.entities.player.util.contract.Contract;

import java.sql.*;

public class NewPlayerInterface extends AbstractInterface {
    private static final String insertPlayerStmt = "";

    public static int addNewPlayer(Player player, int teamID, int leagueID, int season) {
        int playerID = insertPlayer(player, teamID);
        insertPlayerAttributes(playerID, player.getPlayerAttributes(), leagueID, season);
        insertPlayerPotentialAttributes(playerID, player.getPotentialAttributes(), leagueID, season);
        // TODO - Insert player stats from league.
        return playerID;
    }

    /**
     * Insert a new player into the database and return their generated playerID.
     *
     * @param player the player to insert.
     * @param teamID the ID of the team the player belongs to.
     * @return the generated playerID of the player.
     */
    public static int insertPlayer(Player player, int teamID) {
        int playerID = -1;
        try (PreparedStatement preparedInsertStmt = connection.prepareStatement(insertPlayerStmt, Statement.RETURN_GENERATED_KEYS)) {
            populateInsertPlayerStmt(player, teamID, preparedInsertStmt);

            int affectedRows = preparedInsertStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating player failed, no rows affected.");
            }
            connection.commit();

            try (ResultSet generatedPlayerIDs = preparedInsertStmt.getGeneratedKeys()) {
                if (generatedPlayerIDs.next()) {
                    playerID = generatedPlayerIDs.getInt(1);
                } else {
                    throw new SQLException("Creating player failed, no ID obtained.");
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return playerID;
    }

    /**
     * Insert player data into the SQL statement.
     *
     * @param player            the player object to retrieve data from.
     * @param teamID            the ID of the team the player belongs to.
     * @param preparedStatement the statement to insert data into.
     * @throws SQLException if data could not be inserted.
     */
    // TODO - Finish populateInsertPlayerStmt()
    protected static void populateInsertPlayerStmt(Player player, int teamID, PreparedStatement preparedStatement) throws SQLException {
        Contract contract = player.getContract();
        Archetype archetype = player.getArchetype();
        preparedStatement.clearParameters();
        preparedStatement.setInt(1, player.getPlayerID());
        preparedStatement.setInt(2, teamID);
        preparedStatement.setString(3, player.getFirstName());
        preparedStatement.setString(4, player.getLastName());
        preparedStatement.setFloat(5, (float) player.getHeight());
        preparedStatement.setFloat(6, (float) player.getWeight());
        preparedStatement.setFloat(7, (float) player.getWingspan());
        preparedStatement.setFloat(8, (float) player.getStandingVertical());
        preparedStatement.setFloat(9, (float) player.getMaxVertical());
        preparedStatement.setString(10, player.getCollege());
        preparedStatement.setDate(11, Date.valueOf(player.getBirthDate()));
        preparedStatement.setInt(12, player.getYearsPro());
        preparedStatement.setInt(13, player.getOverall());
        preparedStatement.setInt(14, player.getPotentialOverall());
        preparedStatement.setFloat(15, (float) contract.getSalary());
        preparedStatement.setInt(16, contract.getContractLength());
        preparedStatement.setInt(17, contract.getYearsRemaining());
        preparedStatement.setBoolean(19, contract.hasNoTradeClause());
        preparedStatement.setString(20, contract.getContractOption().toString());
        preparedStatement.setInt(21, player.getPlayerID()); // TODO - Archetype ID mapping.
        preparedStatement.setString(22, player.getPosition().getShortName());
        preparedStatement.setString(23, player.getSecondaryPosition().getShortName());
    }

    /**
     * @param playerID   the ID of the player whose attributes are being inserted.
     * @param attributes the player's attributes.
     * @param leagueID   the ID of the league the player belongs to.
     * @param season     the current season the league is in.
     */
    // TODO - Finish insertPlayerAttributes()
    public static void insertPlayerAttributes(int playerID, PlayerAttributes attributes, int leagueID, int season) {
    }

    /**
     * @param playerID   the ID of the player whose attributes are being inserted.
     * @param attributes the player's potential attributes.
     * @param leagueID   the ID of the league the player belongs to.
     * @param season     the current season the league is in.
     */
    // TODO - Finish insertPlayerPotentialAttributes()
    public static void insertPlayerPotentialAttributes(int playerID, PlayerAttributes attributes, int leagueID, int season) {
    }

    public static void insertPlayerStats(int playerID, int leagueID, int season) {

    }
}
