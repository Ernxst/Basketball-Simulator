package com.example.entities.league;

import com.example.entities.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "LEAGUE_STANDINGS")
@IdClass(LeagueStandings.LeagueStandingsKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueStandings {
    // Fields
    @Id
    @Column(name = "LEAGUE_ID", nullable = false)
    private int leagueID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Id
    @Column(name = "TEAM_ID", nullable = false)
    private int teamID;

    @Column(name = "WINS")
    private int wins;

    @Column(name = "LOSSES")
    private int losses;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID", insertable = false, updatable = false)
    @JoinColumn(name = "SEASON", insertable = false, updatable = false)
    private LeagueSeason leagueSeason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeagueStandingsKey implements Serializable {
        private int leagueID;
        private int season;
        private int teamID;
    }

    @Override
    public String toString() {
        return "League Standings {" +
                "\n    leagueID: " + leagueID +
                "\n    season  : " + season +
                "\n    teamID  : " + teamID +
                "\n    wins    : " + wins +
                "\n    losses  : " + losses +
                "\n    team    : " + team +
                "\n}";
    }
}
