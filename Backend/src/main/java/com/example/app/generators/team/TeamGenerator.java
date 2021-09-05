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

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

/**
 * A class to randomly generate a team.
 */
public class TeamGenerator {
    private final TeamService teamService;
    private final NameService nameService;
    private final PlayerService playerService;

    private final String userTeamName;
    private final String userTeamState;
    private final List<String> existingTeamStates = new ArrayList<>();
    private final List<String> availableTeamNames;
    private final List<String> availableTeamStates;

    public TeamGenerator(TeamService teamService, NameService nameService, PlayerService playerService, String userTeamName, String userTeamState) {
        this.teamService = teamService;
        this.nameService = nameService;
        this.playerService = playerService;
        this.userTeamName = userTeamName;
        this.userTeamState = userTeamState;
        this.availableTeamNames = new ArrayList<>(nameService.getTeamNames());
        this.availableTeamNames.add(userTeamName);
        this.availableTeamStates = new ArrayList<>(nameService.getTeamStates());
    }

    /**
     * Generate a CPU team.
     *
     * @param league          the league the team plays in.
     * @param leagueStartDate the date the league started.
     * @return a CPU team.
     */
    public Team generateAiTeam(League league, LocalDate leagueStartDate) {
        String state = Util.randomChoice(availableTeamStates);
        String name = Util.randomChoice(availableTeamNames);
        // Ensures there are no more than the maximum number of teams in a single state.
        int frequency = Collections.frequency(existingTeamStates, state);
        while (frequency >= TeamConstants.MAX_TEAMS_IN_STATE ||
                (state.equals(userTeamState) && frequency == TeamConstants.MAX_TEAMS_IN_STATE - 1)) {
            availableTeamStates.remove(state);
            state = Util.randomChoice(availableTeamStates);
            frequency = Collections.frequency(existingTeamStates, state);
        }
        return generateTeam(league, state, name, false, leagueStartDate);
    }

    /**
     * Generate the user's team, with the given state and name.
     *
     * @param league          the league the team plays in.
     * @param leagueStartDate the date the league started.
     * @return a team for the user.
     */
    public Team generateUserTeam(League league, LocalDate leagueStartDate) {
        return generateTeam(league, userTeamState, userTeamName, true, leagueStartDate);
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
        team.setTeamID(teamID);
        Map<Integer, Player> players = getPlayers(team, leagueStartDate);
        team.setPlayers(players);
        availableTeamNames.remove(name);
        existingTeamStates.add(state);
        return team;
    }

    /**
     * Insert players into the database and retrieve their IDs into a map.
     *
     * @param team the team the players play for.
     * @return a map of IDs to players.
     */
    private Map<Integer, Player> getPlayers(Team team, LocalDate leagueStartDate) {
        Player[] players = generatePlayers(team, leagueStartDate);
        Map<Integer, Player> mappedPlayers = new HashMap<>();
        Iterable<Player> savedPlayers = playerService.insertPlayers(players);
        for (Player player : savedPlayers) {
            mappedPlayers.put(player.getPlayerID(), player);
        }
        return mappedPlayers;
    }

    /**
     * Generate the players in the team.
     *
     * @param team            the team to generate players for.
     * @param leagueStartDate the date the league started.
     * @return the players in the team.
     */
    private Player[] generatePlayers(Team team, LocalDate leagueStartDate) {
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
                Player player = playerGenerator.generatePlayer(yearsSinceStart, position);
                players[i] = player;
                player.setTeam(team);
                positionFrequency.merge(position, 1, Integer::sum);
                i++;
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
