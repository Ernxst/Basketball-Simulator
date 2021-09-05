package com.example.entities.league;

import com.example.entities.player.FreeAgent;
import com.example.entities.player.Player;
import com.example.entities.team.Team;
import com.example.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "LEAGUE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEAGUE_ID", nullable = false)
    private int leagueID;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "LAST_PLAYED", nullable = false)
    private LocalDate lastPlayed;

    // Relationships
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<LeagueSeason> seasons;

    // Teams & Players
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    @MapKeyColumn(name = "TEAM_ID")
    // { teamID: Team }
    private Map<Integer, Team> teams;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // { playerID: FreeAgent }
    @MapKeyColumn(name = "PLAYER_ID")
    private Map<Integer, FreeAgent> freeAgents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNAME", nullable = false)
    private User user;

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

    public FreeAgent getFreeAgentByID(int playerID) {
        return freeAgents.get(playerID);
    }

    public List<FreeAgent> getAllFreeAgentsAsArray() {
        return new ArrayList<>(freeAgents.values());
    }

    public void setLeagueID(int leagueID) {
        this.leagueID = leagueID;
    }

    public LeagueSeason getSeason(int season) {
        return seasons.get(season);
    }

    /**
     * @return
     */
    public LeagueSeason newSeason() {
        int numOfSeasons = getCurrentSeason();
        LocalDate leagueStart = getStartDate();
        int startYear = leagueStart.getYear();
        int year = numOfSeasons == 0 ? startYear : startYear + 1;
        LocalDate date = LocalDate.of(year, LeagueConstants.LEAGUE_START_MONTH, LeagueConstants.LEAGUE_START_DAY);

        LeagueSeason leagueSeason = new LeagueSeason();
        int season = numOfSeasons + 1;
        List<LeagueRecord> records = newLeagueRecord(season, leagueSeason);
        List<LeagueStandings> standings = newLeagueStandings(season, leagueSeason);
        List<PlayerStats> playerStats = newPlayerStats(season, leagueSeason);

        leagueSeason.setLeagueID(leagueID);
        leagueSeason.setSeason(season);
        leagueSeason.setCurrentDate(date);
        leagueSeason.setLeagueRecords(records);
        leagueSeason.setLeagueStandings(standings);
        leagueSeason.setPlayerStats(playerStats);
        leagueSeason.setLeague(this);

        seasons.add(leagueSeason);
        return leagueSeason;
    }

    /**
     * @param season
     * @param leagueSeason
     * @return
     */
    public List<LeagueRecord> newLeagueRecord(int season, LeagueSeason leagueSeason) {
        List<LeagueRecord> records = new ArrayList<>();
        for (LeagueRecord.Record record : LeagueRecord.Record.allRecords) {
            String title = record.getLabel();
            LeagueRecord newRecord = new LeagueRecord();
            newRecord.setLeagueID(leagueID);
            newRecord.setSeason(season);
            newRecord.setTitle(title);
            newRecord.setPlayerID(null);
            newRecord.setValue(0);
            newRecord.setDateSet(null);
            newRecord.setLeagueSeason(leagueSeason);
            records.add(newRecord);
        }
        return records;
    }

    /**
     * @param season
     * @param leagueSeason
     * @return
     */
    public List<LeagueStandings> newLeagueStandings(int season, LeagueSeason leagueSeason) {
        List<LeagueStandings> standings = new ArrayList<>();
        for (Team team : teams.values()) {
            LeagueStandings leagueStandings = new LeagueStandings();
            leagueStandings.setLeagueID(leagueID);
            leagueStandings.setSeason(season);
            leagueStandings.setTeamID(team.getTeamID());
            leagueStandings.setLeagueSeason(leagueSeason);
            leagueStandings.setTeam(team);
            standings.add(leagueStandings);
        }
        return standings;
    }

    /**
     * @param season
     * @param leagueSeason
     * @return
     */
    public List<PlayerStats> newPlayerStats(int season, LeagueSeason leagueSeason) {
        List<PlayerStats> stats = new ArrayList<>();
        for (Player player : getAllPlayersAsArray()) {
            PlayerStats playerStats = new PlayerStats();
            playerStats.setLeagueID(leagueID);
            playerStats.setPlayerID(player.getPlayerID());
            playerStats.setSeason(season);
            playerStats.setPlayer(player);
            playerStats.setLeagueSeason(leagueSeason);
            stats.add(playerStats);
        }
        return stats;
    }

    public int getCurrentSeason() {
        return seasons.size();
    }

    public LocalDate getCurrentDate() {
        LeagueSeason season = seasons.get(getCurrentSeason() - 1);
        return season.getCurrentDate();
    }

    public void setCurrentDate(LocalDate newDate) {
        LeagueSeason season = seasons.get(getCurrentSeason() - 1);
        season.setCurrentDate(newDate);
    }

    @Override
    public String toString() {
        return "League " + name + " {" +
                "\n    Belongs to           : " + getUser().getUsername() +
                "\n    League ID            : " + leagueID +
                "\n    Start date           : " + startDate +
                "\n    Current Season       : " + getCurrentSeason() +
                "\n    Current Date         : " + getCurrentDate() +
                "\n    Number of Teams      : " + teams.size() +
                "\n    Number of Players    : " + getAllPlayersAsArray().size() +
                "\n    Number of Free Agents: " + getAllFreeAgentsAsArray().size() +
                "\n    Teams                : " + teams +
                "\n    Free Agents          : " + freeAgents +
                "\n}";
    }
}
