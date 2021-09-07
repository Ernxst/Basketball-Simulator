package com.example.entities.player;

import com.example.entities.player.util.contract.Contract;
import com.example.entities.team.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    // { seasonNo: Contract }
    protected List<Contract> allContracts;
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;

    public int getTeamID() {
        return team.getTeamID();
    }

    public void setContract(int season, Contract contract) {
        try {
            allContracts.set(season - 1, contract);
        } catch (IndexOutOfBoundsException ignored) {
            allContracts.add(season - 1, contract);
        }
    }

    public Contract getContract(int season) {
        if (season > allContracts.size())
            return null;
        return allContracts.get(season - 1);
    }

    public Contract getContract() {
        return getContract(team.getCurrentSeason());
    }

    public void setContract(Contract contract) {
        setContract(team.getCurrentSeason(), contract);
    }

    /**
     * Turn a player into a free agent.
     *
     * @return a free agent of this player, with no contract or team.
     */
    public FreeAgent toFreeAgent() {
        FreeAgent freeAgent = new FreeAgent();
        freeAgent.setFirstName(firstName);
        freeAgent.setLastName(lastName);
        freeAgent.setPosition(getPosition());
        freeAgent.setSecondaryPosition(getSecondaryPosition());
        freeAgent.setHeight(height);
        freeAgent.setWeight(weight);
        freeAgent.setWingspan(wingspan);
        freeAgent.setStandingVertical(standingVertical);
        freeAgent.setMaxVertical(maxVertical);
        freeAgent.setArchetype(getArchetype());
        freeAgent.setCollege(college);
        freeAgent.setBirthDate(birthDate);
        freeAgent.setYearsPro(yearsPro);
        freeAgent.setOverall(overall);
        freeAgent.setPotentialOverall(potentialOverall);
        return freeAgent;
    }

    @Override
    public String toString() {
        String playerString = super.toString();
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(playerString.split("\n")));
        splitString.add(2, "        Team ID  :                  " + getTeamID());
        splitString.add(21, "        Contract :                  " + getContract());
        return String.join("\n", splitString) + "\n}";
    }
}
