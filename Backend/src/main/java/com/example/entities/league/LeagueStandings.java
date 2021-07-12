package com.example.entities.league;

import com.example.entities.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;
import org.hibernate.annotations.JoinFormula;

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
    @Column(name = "LEAGUE_ID", nullable = false, insertable = false, updatable = false)
    private int leagueID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Id
    @Column(name = "TEAM_ID", nullable = false, insertable = false, updatable = false)
    private int teamID;

    @Column(name = "WINS", nullable = false)
    private int wins;

    @Column(name = "LOSSES", nullable = false)
    private int losses;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID")),
            })
//    @JoinColumn(name = "LEAGUE_ID", nullable = false, insertable = false, updatable= false)
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "LEAGUE_ID", nullable = false, insertable = false, updatable= false)
//    @JoinColumn(name = "SEASON", nullable = false)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID",
                            insertable = false, updatable = false)),
                    @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "SEASON", value = "SEASON"))
            })
    private LeagueSeason leagueSeason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
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
}
