package com.example.entities.league;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "LEAGUE_SEASON")
@IdClass(LeagueSeason.LeagueSeasonKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueSeason {
    @Id
    @Column(name = "LEAGUE_ID", nullable = false)
    private int leagueID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Column(name = "GAME_DATE", nullable = false)
    private LocalDate currentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID", insertable = false, updatable = false)
    private League league;

    @OneToMany(mappedBy = "leagueSeason", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LeagueStandings> leagueStandings;

    @OneToMany(mappedBy = "leagueSeason", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LeagueRecord> leagueRecords;

    @OneToMany(mappedBy = "leagueSeason", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PlayerStats> playerStats;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeagueSeasonKey implements Serializable {
        private int leagueID;
        private int season;
    }

    @Override
    public String toString() {
        return "League Season {" +
                "\n    leagueID        : " + leagueID +
                "\n    season          : " + season +
                "\n    currentDate     : " + currentDate +
                "\n    leagueStandings : " + leagueStandings +
                "\n    leagueRecords   : " + leagueRecords +
                "\n    playerStats     : " + playerStats +
                "\n}";
    }
}
