package com.example.entities.league.standings.team;

import com.google.common.collect.TreeBasedTable;
import com.example.entities.league.standings.AbstractLeaderboard;

public class TeamStandings extends AbstractLeaderboard {
    public TeamStandings(TreeBasedTable<Integer, Column, Integer> standings) {
        super("Standings", standings, new Column[5]);
    }

    public TeamStandings() {
        super("Standings", new Column[5]);
    }
}
