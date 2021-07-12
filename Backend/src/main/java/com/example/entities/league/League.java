package com.example.entities.league;

import com.example.entities.player.FreeAgent;
import com.example.entities.player.Player;
import com.example.entities.team.Team;
import com.example.entities.user.User;
import com.ibm.db2.cmx.annotation.Table;
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

    @Column(name = "USERNAME", nullable = false, insertable = false, updatable = false)
    private String username;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "LAST_PLAYED", nullable = false)
    private LocalDate lastPlayed;

    // Map of all seasons
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    private List<LeagueSeason> seasons = new ArrayList<>();

    // Teams & Players
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // { teamID: Team }
    private Map<Integer, Team> teams;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // { playerID: FreeAgent }
    private Map<Integer, FreeAgent> freeAgents;

    // Standings
    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // seasonNo: { playerID: PlayerStats }
    private Map<Integer, PlayerStats> allSeasonsPlayerStats;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // seasonNo: { teamID: LeagueStandings }
    private Map<Integer, LeagueStandings> allSeasonsTeamStandings;

    @OneToMany(mappedBy = "league", fetch = FetchType.LAZY)
    // seasonNo: { title: LeagueRecord }
    private Map<String, LeagueRecord> allSeasonsLeagueRecords;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERNAME", nullable = false, insertable = false, updatable = false)
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

    public LeagueSeason newSeason() {
        int numOfSeasons = getCurrentSeason();
        LocalDate leagueStart = getStartDate();
        int startYear = leagueStart.getYear();
        int year = numOfSeasons == 0 ? startYear : startYear + 1;
        LocalDate date = LocalDate.of(year, LeagueConstants.LEAGUE_START_MONTH, LeagueConstants.LEAGUE_START_DAY);
        LeagueSeason leagueSeason = new LeagueSeason(getLeagueID(), numOfSeasons + 1, date, this);
        seasons.add(leagueSeason);
        return leagueSeason;
    }

    public int getCurrentSeason() {
        return seasons.size();
    }

    public LocalDate getCurrentDate() {
        LeagueSeason season = seasons.get(getCurrentSeason());
        return season.getCurrentDate();
    }

    public void setCurrentDate(LocalDate newDate) {
        LeagueSeason season = seasons.get(getCurrentSeason());
        season.setCurrentDate(newDate);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("League " + name + " {" +
                "\n    League ID            : " + leagueID +
                "\n    Start date           : " + startDate +
                "\n    Current Season       : " + getCurrentSeason() +
//                "\n    Current Date         : " + getCurrentDate() +
                "\n    Number of Teams      : " + teams.size() +
                "\n    Number of Players    : " + getAllPlayersAsArray().size() +
                "\n    Number of Free Agents: " + getAllFreeAgentsAsArray().size() +
                "\n    League Records       : ");

//        for (Map.Entry<LeagueRecord.Record, LeagueRecord> entry : leagueRecords.entrySet()) {
//            output.append("\n        ").append(entry.getKey().getLabel()).append(": ");
//            String[] record = entry.getValue().toString().split("\n");
//            for (String line : record)
//                output.append("\n            ").append(line);
//        }
        output.append("\n    teams=")
                .append(teams)
                .append("\n    freeAgents=")
                .append(freeAgents)
                .append("\n    playerStandings  :")
                .append(allSeasonsPlayerStats)
                .append("\n    teamStandings=")
                .append(allSeasonsTeamStandings)
                .append("\n}");
        return output.toString();
    }
}
