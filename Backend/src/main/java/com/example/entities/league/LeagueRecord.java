package com.example.entities.league;

import com.example.entities.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "LEAGUE_RECORD")
@IdClass(LeagueRecord.LeagueRecordKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeagueRecord {
    // Fields
    @Id
    @Column(name = "LEAGUE_ID", nullable = false)
    private int leagueID;

    @Id
    @Column(name = "PLAYER_ID")
    private Integer playerID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Id
    @Column(name = "RECORD_TITLE", nullable = false)
    private String title;

    @Column(name = "RECORD_VALUE")
    private int value;

    @Column(name = "DATE_SET")
    private LocalDate dateSet;

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
    public static class LeagueRecordKey implements Serializable {
        private int leagueID;
        private int season;
        private String title;
    }

    public enum Record {
        MOST_PTS("Most Points"), MOST_REB("Most Rebounds"),
        MOST_AST("Most Assists"), MOST_STL("Most Steals"),
        MOST_BLK("Most Blocks"), MOST_TO("Most Turnovers"),
        MOST_FTA("Most Free Throws Attempted"), MOST_FTM("Most Free Throws Made"),
        MOST_FGA("Most Field Goals Attempted"), MOST_FGM("Most Field Goals Made"),
        MOST_3PA("Most Three Pointers Attempted"), MOST_3PM("Most Three Pointers Made");

        public static final Record[] allRecords = values();
        private final String label;

        Record(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\n    Player ID: " + playerID +
                "\n    Player   : " + player +
                "\n    Title    : " + title +
                "\n    Value    : " + value +
                "\n    Season   : " + season +
                "\n    Date Set : " + dateSet +
                "\n}";
    }
}
