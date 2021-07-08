package com.example.entities.league.standings.player;

import com.example.entities.league.standings.AbstractLeaderboard;
import com.google.common.collect.TreeBasedTable;

import java.util.Arrays;
import java.util.List;

public class PlayerStandings extends AbstractLeaderboard {
    public PlayerStandings(TreeBasedTable<Integer, Column, Integer> standings) {
        super("League Leaders", standings, new Column[5]);
    }

    public PlayerStandings() {
        super("League Leaders", new Column[5]);
    }

    public static void main(String[] args) {
        PlayerStandings playerStandings = new PlayerStandings();
        List<Integer> values = Arrays.asList(32, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        playerStandings.addRow(0, values);

        values = Arrays.asList(23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        playerStandings.addRow(values);

        values = Arrays.asList(96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        playerStandings.addRow(values);

        System.out.println(playerStandings.toString());
        System.out.println(playerStandings.toJSON());
        System.out.println(playerStandings.toJSON(true));
        System.out.println(playerStandings.sort(SortDirection.ASC));
        System.out.println(playerStandings.sort(SortDirection.DESC));
        System.out.println(playerStandings.sortBy(Column.PTS, SortDirection.ASC));
        System.out.println(playerStandings.sortBy(Column.PTS, SortDirection.DESC));
        System.out.println(playerStandings.sortBy(Column.AST, SortDirection.DESC));
    }
}
