package com.example.app.generators.league;

import com.example.app.generators.player.FreeAgentGenerator;
import com.example.app.generators.team.TeamGenerator;
import com.example.app.util.Util;
import com.example.entities.league.League;
import com.example.entities.league.LeagueConstants;
import com.example.entities.player.FreeAgent;
import com.example.entities.team.Team;
import com.example.entities.user.User;
import com.example.services.NameService;
import com.example.services.freeAgent.FreeAgentService;
import com.example.services.league.LeagueService;
import com.example.services.league.season.LeagueSeasonService;
import com.example.services.player.PlayerService;
import com.example.services.team.TeamService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class LeagueGenerator {
    private final LeagueService leagueService;
    private final FreeAgentService freeAgentService;
    private final TeamService teamService;
    private final NameService nameService;
    private final PlayerService playerService;
    private final LeagueSeasonService leagueSeasonService;

    /**
     * Randomly generate a new league instance.
     *
     * @param user       the user to generate the league for.
     * @param name       the name of the league (to be stored).
     * @param startDate  the date the league was started.
     * @param numOfTeams the number of teams to generate.
     * @param teamName   the name of the user's team.
     * @param state      the state the user's team is located in.
     * @return a new randomly generated league instance.
     */
    public League generateLeague(User user, String name, LocalDate startDate, int numOfTeams, String teamName, String state) {
        League league = new League();
        league.setName(name);
        league.setStartDate(startDate);
        league.setLastPlayed(LocalDate.now());
        league.setTeams(new HashMap<>());
        league.setFreeAgents(new HashMap<>());
        league.setSeasons(new ArrayList<>());
        league.setUser(user);

        int leagueID = leagueService.insertLeague(league);
        Map<Integer, Team> teams = generateTeams(league, numOfTeams, startDate, teamName, state);
        Map<Integer, FreeAgent> freeAgents = generateFreeAgents(league, numOfTeams, startDate);
        league.setTeams(teams);
        league.setFreeAgents(freeAgents);
        league.setLeagueID(leagueID);

        leagueSeasonService.insertNewSeason(league);
        return league;
    }

    /**
     * Generate a map of basketball teams.
     *
     * @param league     the league the teams will belong to.
     * @param numOfTeams the number of teams to generate.
     * @param startDate  the date the league was started.
     * @param teamName   the name of the user's team.
     * @param state      the state the user's team is located in.
     * @return a map of randomly generated teams, including the user's team.
     */
    private Map<Integer, Team> generateTeams(League league, int numOfTeams, LocalDate startDate,
                                             String teamName, String state) {
        Map<Integer, Team> teams = new HashMap<>();
        TeamGenerator teamGenerator = new TeamGenerator(teamService, nameService, playerService, teamName, state);

        for (int i = 0; i < numOfTeams - 1; i++) {
            Team team = teamGenerator.generateAiTeam(league, startDate);
            teams.put(team.getTeamID(), team);
        }

        // TODO - Note that user cannot enter their own team name as it is not in database.
        Team userTeam = teamGenerator.generateUserTeam(league, startDate);
        teams.put(userTeam.getTeamID(), userTeam);
        return teams;
    }

    /**
     * Generate a map of free agents available to sign.
     *
     * @param league     the league the free agents will belong to.
     * @param numOfTeams the number of teams in the league, which dictates the number of free agents.
     * @return a map of randomly generated free agents.
     */
    private Map<Integer, FreeAgent> generateFreeAgents(League league, int numOfTeams, LocalDate leagueStartDate) {
        int yearsSinceStart = Util.yearsBetweenDateAndToday(leagueStartDate);
        int numOfPlayers = Util.randomInt(numOfTeams * LeagueConstants.MIN_FREE_AGENTS_MULTIPLIER, numOfTeams * LeagueConstants.MAX_FREE_AGENTS_MULTIPLIER + 1);
        Map<Integer, FreeAgent> freeAgents = new HashMap<>();
        FreeAgentGenerator freeAgentGenerator = new FreeAgentGenerator(nameService);
        for (int i = 0; i < numOfPlayers; i++) {
            FreeAgent freeAgent = freeAgentGenerator.generateFreeAgent(yearsSinceStart);
            freeAgent.setLeague(league);
            int playerID = freeAgentService.insertFreeAgent(freeAgent);
            freeAgent.setPlayerID(playerID);
            freeAgents.put(playerID, freeAgent);
        }
        return freeAgents;
    }
}
