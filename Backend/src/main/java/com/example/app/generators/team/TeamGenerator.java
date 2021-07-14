package com.example.app.generators.team;

import com.example.app.generators.player.PlayerGenerator;
import com.example.app.util.Util;
import com.example.entities.league.League;
import com.example.entities.player.Player;
import com.example.entities.player.util.Position;
import com.example.entities.team.Team;
import com.example.entities.team.TeamConstants;
import com.example.services.NameService;
import com.example.services.player.PlayerService;
import com.example.services.team.TeamService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * A class to randomly generate a team.
 */
@AllArgsConstructor
public class TeamGenerator {
    private final TeamService teamService;
    private final NameService nameService;
    private final PlayerService playerService;

    /**
     * Generate a CPU team.
     *
     * @param league          the league the team plays in.
     * @param leagueStartDate the date the league started.
     * @param existingNames   an array of the team names of teams that have already been generated.
     * @param existingStates  an array of the team states of teams that have already been generated.
     * @param userTeamName    the name of the user's team.
     * @param userTeamState   the state the user's team is located in.
     * @return a CPU team.
     */
    public Team generateTeam(League league, LocalDate leagueStartDate,
                             List<String> existingNames, List<String> existingStates,
                             String userTeamName, String userTeamState) {
        String state = nameService.randomTeamState();
        String name = nameService.randomTeamName();
        // Ensures no duplicate team names.
        while (existingNames.contains(name) || name.equals(userTeamName))
            name = nameService.randomTeamName();
        // Ensures there are no more than the maximum number of teams in a single state.
        int frequency = Collections.frequency(existingStates, state);
        while (frequency >= TeamConstants.MAX_TEAMS_IN_STATE ||
                state.equals(userTeamState) && frequency == TeamConstants.MAX_TEAMS_IN_STATE - 1)
            state = nameService.randomTeamState();
        return generateTeam(league, state, name, false, leagueStartDate);
    }

    /**
     * Generate the user's team, with the given state and name.
     *
     * @param league          the league the team plays in.
     * @param state           the state the team is located in.
     * @param name            the name of the team.
     * @param leagueStartDate the date the league started.
     * @return a team for the user.
     */
    public Team generateTeam(League league, String state, String name, LocalDate leagueStartDate) {
        return generateTeam(league, state, name, true, leagueStartDate);
    }

    /**
     * Private method to generate a team.
     *
     * @param league          the league the team plays in.
     * @param state           the state the team is located in.
     * @param name            the name of the team.
     * @param isUserTeam      whether the team being generated is the user or CPU team.
     * @param leagueStartDate the date the league started.
     * @return a team.
     */
    private Team generateTeam(League league, String state, String name, boolean isUserTeam, LocalDate leagueStartDate) {
        Player[] players = generatePlayers(leagueStartDate);
        LocalDate dateFounded = isUserTeam ? leagueStartDate : Util.randomDate(leagueStartDate, LocalDate.now());
        int iconID = teamService.randomTeamIconID();
        Team team = new Team();
        team.setState(state);
        team.setName(name);
        team.setRelocations(0);
        team.setRenames(0);
        team.setUserTeam(isUserTeam);
        team.setDateFounded(dateFounded);
        team.setIconID(iconID);
        team.setPlayers(new HashMap<>());
        team.setAllStandings(new HashMap<>());
        team.setLeague(league);
        int teamID = teamService.insertTeam(team);
        Map<Integer, Player> mappedPlayers = getPlayerIDs(players, team);
        team.setPlayers(mappedPlayers);
        teamService.insertTeam(team);
        return team;
    }

    /**
     * Insert players into the database and retrieve their IDs into a map.
     *
     * @param players the players to insert.
     * @param team    the team the players play for.
     * @return a map of IDs to players.
     */
    private Map<Integer, Player> getPlayerIDs(Player[] players, Team team) {
        Map<Integer, Player> mappedPlayers = new HashMap<>();
        for (Player player : players) {
            player.setTeam(team);
            int playerID = playerService.insertPlayer(player);
            player.setPlayerID(playerID);
            mappedPlayers.put(playerID, player);
        }
        return mappedPlayers;
    }

    /**
     * Generate the players in the team.
     *
     * @param leagueStartDate the date the league started.
     * @return the players in the team.
     */
    private Player[] generatePlayers(LocalDate leagueStartDate) {
        int yearsSinceStart = Util.yearsBetweenDateAndToday(leagueStartDate);
        int numOfPlayers = Util.randomInt(TeamConstants.MIN_PLAYERS, TeamConstants.MAX_PLAYERS + 1);
        Map<Position, Integer> playersInEachPosition = generatePositionNumbers(numOfPlayers);
        Map<Position, Integer> positionFrequency = new HashMap<>();
        Player[] players = new Player[numOfPlayers];
        PlayerGenerator playerGenerator = new PlayerGenerator(nameService);

        int i = 0;
        for (Position position : Position.positions) {
            int maxPlayersInPosition = playersInEachPosition.get(position);
            positionFrequency.put(position, 1);
            while (positionFrequency.get(position) <= maxPlayersInPosition) {
                Player player = playerGenerator.generatePlayer(yearsSinceStart);
                Position playerPosition = player.getPosition();
                if (playerPosition.equals(position)) {
                    players[i] = player;
                    positionFrequency.merge(position, 1, Integer::sum);
                    i++;
                }
            }
        }
        return players;
    }

    /**
     * Given the number of players to be generated, calculate how many players should be in each position.
     *
     * @param numOfPlayers the number of players to be generated.
     * @return the number of players in each position.
     */
    private Map<Position, Integer> generatePositionNumbers(int numOfPlayers) {
        // Ensures there is always at least one player in each position
        Position[] sample = Position.randomPositions(numOfPlayers - Position.numOfPositions);
        Map<Position, Integer> distribution = Arrays.stream(sample).collect(groupingBy(Function.identity(), summingInt(e -> 1)));
        for (Position position : Position.positions) {
            distribution.merge(position, TeamConstants.MIN_PLAYERS_IN_EACH_POSITION, Integer::sum);
        }
        return distribution;
    }
}
