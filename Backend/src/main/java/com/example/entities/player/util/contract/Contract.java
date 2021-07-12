package com.example.entities.player.util.contract;

import com.example.entities.league.League;
import com.example.entities.league.LeagueSeason;
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

/**
 * A class representing a player's contract.
 */

@Entity
@Table(name = "CONTRACT")
@IdClass(Contract.ContractKey.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contract {
    @Id
    @Column(name = "PLAYER_ID", nullable = false, insertable = false, updatable = false)
    private int playerID;

    @Id
    @Column(name = "LEAGUE_ID", nullable = false, insertable = false, updatable = false)
    private int leagueID;

    @Id
    @Column(name = "SEASON", nullable = false)
    private int season;

    @Column(name = "SALARY", nullable = false)
    private double salary;

    @Column(name = "CONTRACT_LENGTH", nullable = false)
    private int contractLength;

    @Column(name = "YEARS_REMAINING", nullable = false)
    private int yearsRemaining;

    @Column(name = "NO_TRADE_CLAUSE", nullable = false)
    private boolean noTradeClause;

    @Column(name = "CONTRACT_OPTION", nullable = false)
    private String contractOption;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAYER_ID", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID")),
            })
    private League league;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumnsOrFormulas(
            value = {
                    @JoinColumnOrFormula(column = @JoinColumn(referencedColumnName = "LEAGUE_ID", name = "LEAGUE_ID",
                            insertable = false, updatable = false)),
                    @JoinColumnOrFormula(formula = @JoinFormula(referencedColumnName = "SEASON", value = "SEASON"))
            })
    private LeagueSeason leagueSeason;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContractKey implements Serializable {
        private int playerID;
        private int leagueID;
        private int season;
    }

    public ContractOption getContractOption() {
        return ContractOption.getByName(contractOption);
    }

    public void setContractOption(ContractOption contractOption) {
       this.contractOption = contractOption.getLabel();
    }

    public boolean hasNoTradeClause() {
        return isNoTradeClause();
    }

    @Override
    public String toString() {
        return "Contract {" +
                "\n    Contract Length:  " + contractLength +
                "\n    Years Remaining:  " + yearsRemaining +
                "\n    Contract Option:  " + getContractOption().getLabel() +
                "\n    Salary ($):       " + salary +
                "\n    No Trade Clause?: " + noTradeClause +
                "\n}";
    }
}
