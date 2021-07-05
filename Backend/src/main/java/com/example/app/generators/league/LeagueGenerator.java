package com.example.app.generators.league;

import com.example.app.generators.player.FreeAgentGenerator;
import com.example.app.generators.team.TeamGenerator;
import com.example.app.util.Util;
import com.example.db.interfaces.freeAgent.FreeAgentInterface;
import com.example.db.interfaces.league.LeagueInterface;
import com.example.entities.league.League;
import com.example.entities.league.LeagueConstants;
import com.example.entities.player.FreeAgent;
import com.example.entities.team.Team;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to randomly generate a new league instance.
 */
public class LeagueGenerator {

    /**
     * Randomly generate a new league instance.
     *
     * @param username   the user to generate the league for
     * @param name       the name of the league (to be stored)
     * @param startDate  the date the league was started.
     * @param numOfTeams the number of teams to generate.
     * @param teamName   the name of the user's team.
     * @param state      the state the user's team is located in.
     * @return a new randomly generated league instance.
     */
    public static League generateLeague(String username, String name, LocalDate startDate, int numOfTeams,
                                        String teamName, String state) {
        League league = new League(name, username, startDate, null, null);
        int leagueID = LeagueInterface.insertLeague(username, league);
        Map<Integer, Team> teams = generateTeams(leagueID, numOfTeams, startDate, teamName, state);
        Map<Integer, FreeAgent> freeAgents = generateFreeAgents(leagueID, numOfTeams, startDate);
        league.setTeams(teams);
        league.setFreeAgents(freeAgents);
        league.setLeagueID(leagueID);
        return league;
    }

    /**
     * Generate a map of basketball teams.
     *
     * @param leagueID   the ID of the league the teams will belong to.
     * @param numOfTeams the number of teams to generate.
     * @param startDate  the date the league was started.
     * @param teamName   the name of the user's team.
     * @param state      the state the user's team is located in.
     * @return a map of randomly generated teams, including the user's team.
     */
    private static Map<Integer, Team> generateTeams(int leagueID, int numOfTeams, LocalDate startDate, String teamName, String state) {
        Map<Integer, Team> teams = new HashMap<>();
        for (int i = 0; i < numOfTeams - 1; i++) {
            Collection<Team> existingTeams = teams.values();
            List<String> existingTeamNames = existingTeams.stream().map(Team::getName).collect(Collectors.toList());
            Team team = TeamGenerator.generateTeam(leagueID, startDate);
            while (existingTeamNames.contains(team.getName())) {
                team = TeamGenerator.generateTeam(leagueID, startDate);
            }
            teams.put(team.getTeamID(), team);
        }
        Team userTeam = TeamGenerator.generateTeam(leagueID, state, teamName, startDate);
        teams.put(userTeam.getTeamID(), userTeam);
        return teams;
    }

    /**
     * Generate a map of free agents available to sign.
     *
     * @param leagueID   the ID of the league the free agents will belong to.
     * @param numOfTeams the number of teams in the league, which dictates the number of free agents.
     * @return a map of randomly generated free agents.
     */
    private static Map<Integer, FreeAgent> generateFreeAgents(int leagueID, int numOfTeams, LocalDate leagueStartDate) {
        int yearsSinceStart = Util.yearsBetweenDateAndToday(leagueStartDate);
        int numOfPlayers = Util.randomInt(numOfTeams * LeagueConstants.MIN_FREE_AGENTS_MULTIPLIER, numOfTeams * LeagueConstants.MAX_FREE_AGENTS_MULTIPLIER + 1);
        Map<Integer, FreeAgent> freeAgents = new HashMap<>();
        for (int i = 0; i < numOfPlayers; i++) {
            FreeAgent freeAgent = FreeAgentGenerator.generateFreeAgent(yearsSinceStart);
            freeAgent.setLeagueID(leagueID);
            int playerID = FreeAgentInterface.insertFreeAgent(freeAgent, leagueID);
            freeAgent.setPlayerID(playerID);
            freeAgents.put(playerID, freeAgent);
        }
        return freeAgents;
    }
}
