package com.example.entities.league;

import com.example.entities.league.standings.player.PlayerStandings;
import com.example.entities.league.standings.team.TeamStandings;
import com.example.entities.player.FreeAgent;
import com.example.entities.player.Player;
import com.example.entities.team.Team;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Entity
public class League {
    private final String name;
    private final String username;
    private final LocalDate startDate;
    //    @ElementCollection
    private final Map<LeagueRecord.Record, LeagueRecord> leagueRecords;
    //    @ElementCollection
    private Map<Integer, Team> teams;
    //    @ElementCollection
    private Map<Integer, FreeAgent> freeAgents;

    private int season;
    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int leagueID;
    private LocalDate currentDate;
    private PlayerStandings playerStandings;
    private TeamStandings teamStandings;

    /**
     * Constructor used when generating a new league.
     *
     * @param name       the name of the league.
     * @param startDate  the date the league started.
     * @param teams      a map of teams, team ID to team mapping.
     * @param freeAgents a map of free agents, player ID to free agent mapping.
     */
    public League(String name, String username, LocalDate startDate, Map<Integer, Team> teams,
                  Map<Integer, FreeAgent> freeAgents) {
        this.name = name;
        this.username = username;
        this.startDate = startDate;
        this.teams = teams;
        this.freeAgents = freeAgents;
        this.leagueRecords = new HashMap<>();
        for (LeagueRecord.Record record : LeagueRecord.Record.records) {
            this.leagueRecords.put(record, new LeagueRecord());
        }
        this.season = 1;
        this.currentDate = LocalDate.of(startDate.getYear(), LeagueConstants.LEAGUE_START_MONTH,
                LeagueConstants.LEAGUE_START_DAY);
        this.playerStandings = new PlayerStandings();
        this.teamStandings = new TeamStandings();
    }

    // Constructor used when loading an existing league from the database.
    public League(String name, String username, LocalDate startDate, Map<LeagueRecord.Record, LeagueRecord> leagueRecords,
                  Map<Integer, Team> teams, Map<Integer, FreeAgent> freeAgents, int season, int leagueID,
                  LocalDate currentDate, PlayerStandings playerStandings, TeamStandings teamStandings) {
        this.name = name;
        this.username = username;
        this.startDate = startDate;
        this.leagueRecords = leagueRecords;
        this.teams = teams;
        this.freeAgents = freeAgents;
        this.season = season;
        this.leagueID = leagueID;
        this.currentDate = currentDate;
        this.playerStandings = playerStandings;
        this.teamStandings = teamStandings;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Map<Integer, Team> getTeams() {
        return teams;
    }

    public void setTeams(Map<Integer, Team> teams) {
        this.teams = teams;
    }

    public Map<Integer, FreeAgent> getFreeAgents() {
        return freeAgents;
    }

    public void setFreeAgents(Map<Integer, FreeAgent> freeAgents) {
        this.freeAgents = freeAgents;
    }

    public Map<LeagueRecord.Record, LeagueRecord> getLeagueRecords() {
        return leagueRecords;
    }

    public LeagueRecord getRecord(LeagueRecord.Record record) {
        return leagueRecords.get(record);
    }

    public League copy() {
        return new League(name, username, startDate, leagueRecords, teams, freeAgents,
                season, leagueID, currentDate, playerStandings, teamStandings);
    }

    public int getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public Map<Integer, Player> getAllPlayers() {
        Map<Integer, Player> players = new HashMap<>();
        for (Team team : teams.values()) {
            players.putAll(team.getPlayers());
        }
        return players;
    }

    public Player getPlayerByID(int playerID) {
        return getAllPlayers().get(playerID);
    }

    public List<Player> getAllPlayersAsArray() {
        List<Player> players = new ArrayList<>();
        for (Team team : teams.values()) {
            players.addAll(team.getPlayers().values());
        }
        return players;
    }

    public Player getFreeAgentByID(int playerID) {
        return freeAgents.get(playerID);
    }

    public List<FreeAgent> getAllFreeAgentsAsArray() {
        return (List<FreeAgent>) freeAgents.values();
    }

    public PlayerStandings getPlayerStandings() {
        return playerStandings;
    }

    public void setPlayerStandings(PlayerStandings playerStandings) {
        this.playerStandings = playerStandings;
    }

    public TeamStandings getTeamStandings() {
        return teamStandings;
    }

    public void setTeamStandings(TeamStandings teamStandings) {
        this.teamStandings = teamStandings;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder(name + " {" +
                "\n    League ID            : " + leagueID +
                "\n    Start date           : " + startDate +
                "\n    Current Season       : " + season +
                "\n    Current Date         : " + currentDate +
                "\n    Number of Teams      : " + teams.size() +
                "\n    Number of Players    : " + getAllPlayersAsArray().size() +
                "\n    Number of Free Agents: " + getAllFreeAgentsAsArray().size() +
                "\n    League Records       : ");

        for (Map.Entry<LeagueRecord.Record, LeagueRecord> entry : leagueRecords.entrySet()) {
            output.append("\n        ").append(entry.getKey().getLabel()).append(": ");
            String[] record = entry.getValue().toString().split("\n");
            for (String line : record)
                output.append("\n            ").append(line);
        }
        output.append("\n    teams=").append(teams).append("\n    freeAgents=").append(freeAgents).append("\n    playerStandings  :").append(playerStandings).append("\n    teamStandings=").append(teamStandings).append("}");
        return output.toString();
    }
}
