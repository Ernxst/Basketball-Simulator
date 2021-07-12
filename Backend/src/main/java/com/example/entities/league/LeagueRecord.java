package com.example.entities.league;

import com.example.entities.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

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
    @Column(name = "LEAGUE_ID", nullable = false, insertable = false, updatable = false)
    private int leagueID;

    @Id
    @Column(name = "PLAYER_ID", nullable = false, insertable = false, updatable = false)
    private int playerID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Id
    @Column(name = "RECORD_TITLE", nullable = false)
    private String title;

    @Column(name = "RECORD_VALUE")
    private int value;

    @Column(name = "DATE_SET", nullable = false)
    private LocalDate dateSet;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID")),
            })
//    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "LEAGUE_ID", nullable = false)
//    @JoinColumn(name = "SEASON", nullable = false)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID", insertable = false, updatable = false)),
                    @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "SEASON", value = "SEASON"))
            })
    private LeagueSeason leagueSeason;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeagueRecordKey implements Serializable {
        private int leagueID;
        private int playerID;
        private int season;
        private String title;
    }

    public int getPlayerID() {
        return player.getPlayerID();
    }

    public int getSeason() {
        return leagueSeason.getSeason();
    }

    @Override
    public String toString() {
        return "{" +
                "\n    Player ID: " + getPlayerID() +
                "\n    Value    : " + value +
                "\n    Season   : " + getSeason() +
                "\n    Date Set : " + dateSet +
                '}';
    }

    public enum Record {
        MOST_PTS("Most Points"), MOST_REB("Most Rebounds"),
        MOST_AST("Most Assists"), MOST_STL("Most Steals"),
        MOST_BLK("Most Blocks"), MOST_TO("Most Turnovers"),
        MOST_FTA("Most Free Throws Attempted"), MOST_FTM("Most Free Throws Made"),
        MOST_FGA("Most Field Goals Attempted"), MOST_FGM("Most Field Goals Made"),
        MOST_3PA("Most Three Pointers Attempted"), MOST_3PM("Most Three Pointers Made");

        public static final Record[] records = values();
        private final String label;

        Record(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }
}
