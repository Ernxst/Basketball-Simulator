package com.example.entities.league;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JoinColumnOrFormula;
import org.hibernate.annotations.JoinColumnsOrFormulas;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

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
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(
                            referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID",
                            insertable = false, updatable = false)),
            })
    private League league;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LeagueSeasonKey implements Serializable {
        private int leagueID;
        private int season;
    }
}
