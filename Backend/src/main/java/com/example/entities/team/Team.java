package com.example.entities.team;

import com.example.entities.league.League;
import com.example.entities.league.LeagueStandings;
import com.example.entities.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "TEAM")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEAM_ID", nullable = false)
    private int teamID;

    @Column(name = "TEAM_STATE", nullable = false)
    private String state;

    @Column(name = "TEAM_NAME", nullable = false)
    private String name;

    @Column(name = "RELOCATIONS", nullable = false)
    private int relocations;

    @Column(name = "RENAMES", nullable = false)
    private int renames;

    @Column(name = "IS_USER_TEAM", nullable = false)
    private boolean isUserTeam;

    @Column(name = "DATE_FOUNDED", nullable = false)
    private LocalDate dateFounded;

    @Column(name = "ICON_ID", nullable = false)
    private int iconID;

    // Relationships
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    @MapKeyColumn(name = "PLAYER_ID", insertable = false, updatable = false)
    // { playerID : Player }
    private Map<Integer, Player> players;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID")
    private League league;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    // { seasonNo: Standings }
    private Map<Integer, LeagueStandings> allStandings;

    public int getLeagueID() {
        return getLeague().getLeagueID();
    }

    public String getFullName() {
        return getState() + " " + getName();
    }

    public double getCapSpace() {
        return TeamConstants.MAX_CAP - getTotalCap();
    }

    public double getCapSpace(int season) {
        return TeamConstants.MAX_CAP - getTotalCap(season);
    }

    public double getTotalCap() {
        return getTotalCap(getCurrentSeason());
    }

    public int getCurrentSeason() {
        return league.getCurrentSeason();
    }

    public double getTotalCap(int season) {
        double totalCap = 0;
        for (Player player : players.values()) {
            totalCap += player.getContract(season).getSalary();
        }
        return totalCap;
    }

    public Player getPlayerById(int playerId) {
        return players.getOrDefault(playerId, null);
    }
}
