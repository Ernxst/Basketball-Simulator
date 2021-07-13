package com.example.entities.league;

import com.example.entities.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PLAYER_STATS")
@IdClass(PlayerStats.PlayerStatsKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerStats {
    @Id
    @Column(name = "LEAGUE_ID", nullable = false)
    private int leagueID;

    @Id
    @Column(name = "PLAYER_ID", nullable = false)
    private int playerID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Column(name = "POINTS")
    private int points;

    @Column(name = "REBOUNDS")
    private int rebounds;

    @Column(name = "ASSISTS")
    private int assists;

    @Column(name = "STEALS")
    private int steals;

    @Column(name = "BLOCKS")
    private int blocks;

    @Column(name = "TURNOVERS")
    private int turnovers;

    @Column(name = "GAMES_PLAYED")
    private int gamesPlayed;

    @Column(name = "FREE_THROWS_ATTEMPTED")
    private int freeThrowsAttempted;

    @Column(name = "FREE_THROWS_MADE")
    private int freeThrowsMade;

    @Column(name = "FIELD_GOALS_ATTEMPTED")
    private int fieldGoalsAttempted;

    @Column(name = "FIELD_GOALS_MADE")
    private int fieldGoalsMade;

    @Column(name = "THREE_POINTERS_ATTEMPTED")
    private int threePointersAttempted;

    @Column(name = "THREE_POINTERS_MADE")
    private int threePointersMade;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ID", insertable = false, updatable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID", insertable = false, updatable = false)
    @JoinColumn(name = "SEASON", insertable = false, updatable = false)
    private LeagueSeason leagueSeason;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlayerStatsKey implements Serializable {
        private int leagueID;
        private int playerID;
        private int season;
    }

    @Override
    public String toString() {
        return "Player Stats {" +
                "\n    leagueID: " + leagueID +
                "\n    playerID: " + playerID +
                "\n    season: " + season +
                "\n    points: " + points +
                "\n    rebounds: " + rebounds +
                "\n    assists: " + assists +
                "\n    steals: " + steals +
                "\n    blocks: " + blocks +
                "\n    turnovers: " + turnovers +
                "\n    gamesPlayed: " + gamesPlayed +
                "\n    freeThrowsAttempted: " + freeThrowsAttempted +
                "\n    freeThrowsMade: " + freeThrowsMade +
                "\n    fieldGoalsAttempted: " + fieldGoalsAttempted +
                "\n    fieldGoalsMade: " + fieldGoalsMade +
                "\n    threePointersAttempted: " + threePointersAttempted +
                "\n    threePointersMade: " + threePointersMade +
                "\n}";
    }
}
