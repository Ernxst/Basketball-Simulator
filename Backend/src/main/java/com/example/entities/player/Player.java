package com.example.entities.player;

import com.example.app.util.MathsUtil;
import com.example.app.util.Util;
import com.example.entities.player.util.contract.Contract;
import com.example.entities.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Map;

/**
 * Class representing a player.
 */

@Entity
@Table(name = "PLAYER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player extends AbstractPlayer {
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;

    public int getTeamID() {
        return team.getTeamID();
    }

    @OneToMany
    // { seasonNo: Contract }
    protected Map<Integer, Contract> allContracts;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getFirstInitialAndSurname() {
        return getFirstName().charAt(0) + ". " + getLastName();
    }

    public int getAge() {
        return Util.yearsBetweenDateAndToday(getBirthDate());
    }

    public void setContract(int season, Contract contract) {
        allContracts.put(season, contract);
    }

    public void setContract(Contract contract) {
        int season = Util.largestKeyInMap(allContracts);
        setContract(season, contract);
    }

    public Contract getContract(int season) {
        return allContracts.get(season);
    }

    public Contract getContract() {
        int season = Util.largestKeyInMap(allContracts);
        return getContract(season);
    }

    public void addContract(int season, Contract contract) {
        allContracts.put(season, contract);
    }

    /**
     * Turn a player into a free agent.
     *
     * @return a free agent of this player, with no contract or team.
     */
    // TODO - Implement toFreeAgent()
    public FreeAgent toFreeAgent() {
        return new FreeAgent();
    }

    @Override
    public String toString() {
        int[] heightFeetInches = MathsUtil.cmToFeetAndInches(height);
        int[] wingspanFeetInches = MathsUtil.cmToFeetAndInches(wingspan);
        return "Player {" +
                "\n        Player ID:                  " + playerID +
                "\n        Team ID:                    " + getTeamID() +
                "\n        First Name:                 '" + firstName + '\'' +
                "\n        Last Name:                  '" + lastName + '\'' +
                "\n        College:                    '" + college + '\'' +
                "\n        Date of Birth (Y-m-d):      " + birthDate +
                "\n        Age:                        " + getAge() +
                "\n        Height (cm):                " + height +
                "\n        Height (ft, in):            " + heightFeetInches[0] + "\"" + heightFeetInches[1] +
                "\n        Wingspan (cm):              " + wingspan +
                "\n        Wingspan (ft, in):          " + wingspanFeetInches[0] + "\"" + wingspanFeetInches[1] +
                "\n        Weight (lbs):               " + weight +
                "\n        Standing Vertical (inches): " + standingVertical +
                "\n        Max Vertical (inches):      " + maxVertical +
                "\n        Archetype:                  " + getArchetype().getLabel() +
                "\n        Primary Position:           " + getPosition().getFullName() +
                "\n        Secondary Position:         " + secondaryPosition +
                "\n        Years in League:            " + yearsPro +
                "\n        Overall:                    " + overall +
                "\n        Potential Overall:          " + potentialOverall +
                "\n        Contract:                   " + getContract() +
//                "\n        Attributes:                 " + playerAttributes +
//                "\n        Potential Attributes:       " + potentialAttributes +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.getPlayerID();
    }
}
