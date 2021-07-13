package com.example.entities.player;

import com.example.entities.league.League;
import com.example.entities.player.util.contract.Contract;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A free agent, a player without a team.
 */
@Entity
@Table(name = "FREE_AGENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FreeAgent extends AbstractPlayer {
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAGUE_ID", nullable = false)
    private League league;

    public int getLeagueID() {
        return league.getLeagueID();
    }

    // TODO - Implement toPlayer()
    public Player toPlayer(Contract contract, int teamID) {
        Player player = new Player();
        return player;
    }

    @Override
    public String toString() {
        String playerString = super.toString();
        ArrayList<String> splitString = new ArrayList<>(Arrays.asList(playerString.split("\n")));
        splitString.removeIf(s -> s.contains("Player {"));
        splitString.add(0, "Free Agent {");
        splitString.add(2, "        League ID:                  " + getLeagueID());
        return String.join("\n", splitString);
    }
}
