package com.example.entities.league.standings.table;

import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CustomTable {
    Table<Integer, Column, Integer> getAll();

    Map<Column, Integer> getRowByIndex(int index);

    List<Map<Column, Integer>> getAllRows();

    Map<Column, Integer> row(int ID);

    Collection<Integer> rowValues(int ID);

    CustomTable rows(int[] IDs);

    Integer[][] rowsValues(int[] IDs);

    Collection<Integer> column(Column column);

    Column[] headers();

    Integer cell(int ID, Column column);

    void addRow(int ID, List<Integer> values);

    void addRow(List<Integer> values);

    void updateCell(int ID, Column column, int value);

    void updateRow(int ID, List<Integer> values);

    Map<Column, Integer> removeRow(int ID);

    void clear();

    boolean isEmpty();

    int size();

    /**
     * Sort by unique ID.
     *
     * @param sortDirection whether to sort in ascending or descending order.
     * @return a table sorted by unique ID.
     */
    CustomTable sort(SortDirection sortDirection);

    CustomTable sortBy(Column column, SortDirection sortDirection);

    Table<Column, Integer, Integer> transpose();

    Integer[] IDs();

    int maxID();

    CustomTable copy();

    String toJSON();

    String toJSON(boolean useShortName);

    enum SortDirection {
        ASC,
        DESC,
    }

    enum Column {
        PTS("Points"), REB("Rebounds"),
        AST("Assists"), STL("Steals"), BLK("Blocks"),
        TO("Turnovers"), GP("Games Played"),
        FTA("Free Throws Attempted"), FTM("Free Throws Made"),
        FGA("Field Goals Attempted"), FGM("Field Goals Made"),
        TPA("Three Point Attempts"), TPM("Three Point Makes");

        public static final Column[] columns = Column.values();
        private final String label;

        Column(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }

    }
}
