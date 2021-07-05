package com.example.entities.player;

import com.example.entities.player.util.Archetype;
import com.example.entities.player.util.Position;
import com.example.entities.player.util.attributes.PlayerAttributes;
import com.example.entities.player.util.contract.Contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A free agent, a player without a team.
 */
public class FreeAgent extends Player {
    private int leagueID;

    public FreeAgent(String firstName, String lastName, Position position, Position secondaryPosition, double height,
                     double weight, double wingspan, double standingVertical, double maxVertical, Archetype archetype,
                     String college, LocalDate birthDate, int yearsPro, int overall, int potentialOverall,
                     PlayerAttributes playerAttributes, PlayerAttributes potentialAttributes) {
        super(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical, maxVertical,
                archetype, college, birthDate, yearsPro, overall, potentialOverall, null, playerAttributes,
                potentialAttributes);
    }

    public FreeAgent(String firstName, String lastName, Position position, Position secondaryPosition, double height,
                     double weight, double wingspan, double standingVertical, double maxVertical, Archetype archetype,
                     String college, LocalDate birthDate, int yearsPro, int overall, int potentialOverall, Contract contract,
                     PlayerAttributes playerAttributes, PlayerAttributes potentialAttributes, int playerID, int teamID) {
        super(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, contract,
                playerAttributes, potentialAttributes, playerID, teamID);
    }

    public FreeAgent(String firstName, String lastName, Position position, Position secondaryPosition, double height,
                     double weight, double wingspan, double standingVertical, double maxVertical, Archetype archetype,
                     String college, LocalDate birthDate, int yearsPro, int overall, int potentialOverall, Contract contract,
                     PlayerAttributes playerAttributes, PlayerAttributes potentialAttributes, int playerID, int teamID, int leagueID) {
        super(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, contract,
                playerAttributes, potentialAttributes, playerID, teamID);
        this.leagueID = leagueID;
    }

    @Override
    public FreeAgent copy() {
        return new FreeAgent(firstName, lastName, position, secondaryPosition, height, weight, wingspan, standingVertical,
                maxVertical, archetype, college, birthDate, yearsPro, overall, potentialOverall, contract,
                playerAttributes, potentialAttributes, playerID, teamID, leagueID);
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    @Override
    public String toString() {
        String playerString = super.toString();
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(playerString.split("\n")));
        splitString.removeIf(s -> s.contains("Player {"));
        splitString.removeIf(s -> s.contains("Team ID:"));
        splitString.removeIf(s -> s.contains("Contract:"));
        splitString.add(0, "Free Agent {");
        splitString.add(2, "        League ID:                  " + leagueID);
        return String.join("\n", splitString);
    }
}
