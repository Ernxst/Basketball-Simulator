package com.example.entities.league.standings;

import com.example.entities.league.standings.table.AbstractCustomTable;
import com.google.common.collect.TreeBasedTable;

public abstract class AbstractLeaderboard extends AbstractCustomTable implements Leaderboard {
    protected final String name;

    public AbstractLeaderboard(String name, TreeBasedTable<Integer, Column, Integer> standings, Column[] columns) {
        super(standings, columns);
        this.name = name;
    }

    public AbstractLeaderboard(String name, Column[] columns) {
        super(TreeBasedTable.create(), columns);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
