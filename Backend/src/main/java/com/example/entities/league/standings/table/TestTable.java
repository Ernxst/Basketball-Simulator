package com.example.entities.league.standings.table;

import com.google.common.collect.TreeBasedTable;

import java.util.Arrays;


public class TestTable extends AbstractCustomTable {
    public TestTable() {
        super();
    }

    public TestTable(Column[] columns) {
        super(columns);
    }

    public TestTable(TreeBasedTable<Integer, Column, Integer> table) {
        super(table);
    }

    public TestTable(TreeBasedTable<Integer, Column, Integer> table, Column[] columns) {
        super(table, columns);
    }

    public static void main(String[] args) {
        TestTable table = new TestTable(new Column[]{Column.PTS, Column.AST, Column.REB,
                Column.BLK, Column.STL, Column.TO});
        table.addRow(Arrays.asList(42, 10, 6, 4, 1, 3));
        table.addRow(Arrays.asList(42, 10, 6, 4, 1, 1));
        table.addRow(Arrays.asList(42, 10, 6, 4, 1, 1));
        table.addRow(Arrays.asList(42, 12, 6, 4, 1, 4));
        table.addRow(Arrays.asList(42, 10, 6, 4, 1, 1));
        System.out.println(table.sortBy(Column.PTS, SortDirection.ASC));
        System.out.println(table.sortBy(Column.PTS, SortDirection.DESC));
        System.out.println(table.sortBy(Column.AST, SortDirection.DESC));
    }
}
