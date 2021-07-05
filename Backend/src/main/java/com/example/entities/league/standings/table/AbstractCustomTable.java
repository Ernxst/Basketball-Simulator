package com.example.entities.league.standings.table;

import com.google.common.collect.Ordering;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeBasedTable;

import java.util.*;

public class AbstractCustomTable implements CustomTable {
    protected final TreeBasedTable<Integer, Column, Integer> table;
    protected final Column[] columns;
    protected final List<Column> columnList;

    public AbstractCustomTable() {
        this.table = TreeBasedTable.create();
        this.columns = Column.values();
        this.columnList = Arrays.asList(columns);
    }

    public AbstractCustomTable(Column[] columns) {
        this.columns = columns;
        this.columnList = Arrays.asList(columns);
        this.table = TreeBasedTable.create();
    }

    public AbstractCustomTable(TreeBasedTable<Integer, Column, Integer> table) {
        this.table = table;
        this.columns = Column.values();
        this.columnList = Arrays.asList(columns);
    }

    public AbstractCustomTable(TreeBasedTable<Integer, Column, Integer> table, Column[] columns) {
        this.table = table;
        this.columns = columns;
        this.columnList = Arrays.asList(columns);
    }

    public Table<Integer, Column, Integer> getAll() {
        return table;
    }

    public Map<Column, Integer> getRowByIndex(int index) {
        List<Map<Column, Integer>> allRows = getAllRows();
        return allRows.get(index);
    }

    public List<Map<Column, Integer>> getAllRows() {
        return new ArrayList<>(table.rowMap().values());
    }

    public Map<Column, Integer> row(int ID) {
        return table.row(ID);
    }

    public Collection<Integer> rowValues(int ID) {
        return row(ID).values();
    }

    public CustomTable rows(int[] IDs) {
        return new AbstractCustomTable(getRows(IDs), columns);
    }

    private TreeBasedTable<Integer, Column, Integer> getRows(int[] IDs) {
        TreeBasedTable<Integer, Column, Integer> result = TreeBasedTable.create();
        for (int ID : IDs) {
            Map<Column, Integer> row = row(ID);
            List<Integer> values = (List<Integer>) row.values();
            for (int i = 0; i < values.size(); i++) {
                result.put(ID, columns[i], values.get(i));
            }
        }
        return result;
    }

    public Integer[][] rowsValues(int[] IDs) {
        Integer[][] result = new Integer[IDs.length][columns.length];
        for (int i = 0; i < IDs.length; i++) {
            Map<Column, Integer> row = row(IDs[i]);
            result[i] = row.values().toArray(new Integer[0]);
        }
        return result;
    }

    public Collection<Integer> column(Column column) {
        return table.column(column).values();
    }

    public Column[] headers() {
        return columns;
    }

    public Integer cell(int ID, Column column) {
        return table.get(ID, column);
    }

    public void addRow(int ID, List<Integer> values) {
        if (columns.length != values.size()) {
            throw new IllegalArgumentException("The number of values supplied does not match the number of columns in the table.");
        }
        for (int i = 0; i < values.size(); i++) {
            table.put(ID, columns[i], values.get(i));
        }
    }

    public void addRow(List<Integer> values) {
        addRow(maxID(), values);
    }

    public void updateCell(int ID, Column column, int value) {
        table.put(ID, column, value);
    }

    public void updateRow(int ID, List<Integer> values) {
        addRow(ID, values);
    }

    public Map<Column, Integer> removeRow(int ID) {
        return table.row(ID);
    }

    public void clear() {
        table.clear();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public CustomTable sort(SortDirection sortDirection) {
        Comparator<Integer> comparator = (sortDirection.equals(SortDirection.ASC)) ?
                Ordering.natural() : Ordering.natural().reverse();
        TreeBasedTable<Integer, Column, Integer> table = TreeBasedTable.create(comparator, Ordering.natural());
        table.putAll(this.table);
        return new AbstractCustomTable(table, columns);
    }

    /**
     * Keeps comparing cell columns until a column is found where the two cells have different values.
     *
     * @param id1          the ID of the first cell.
     * @param id2          the ID of the first cell.
     * @param column       the previously checked column.
     * @param comparisonNo how many comparisons have previously been made.
     * @return an integer representing whether the cell at (id1, column) is more than (id2, column)
     */
    private int compareCells(Integer id1, Integer id2, Column column, int comparisonNo) {
        if (comparisonNo == columns.length - 1)
            return 1; // Maintain insertion order
        int index = (columnList.indexOf(column) + 1) % columns.length;
        Column newColumn = columns[index];
        Integer cell1 = cell(id1, newColumn);
        Integer cell2 = cell(id2, newColumn);
        return (cell1.equals(cell2)) ? compareCells(id1, id2, newColumn, comparisonNo + 1) : cell1.compareTo(cell2);
    }

    public CustomTable sortBy(Column column, SortDirection sortDirection) {
        Comparator<Integer> comparator = (id1, id2) -> {
            if (id1.equals(id2))
                return 0;
            Integer cell1 = cell(id1, column);
            Integer cell2 = cell(id2, column);
            return (cell1.equals(cell2)) ? compareCells(id1, id2, column, 0) : cell1.compareTo(cell2);
        };
        comparator = (sortDirection.equals(SortDirection.ASC)) ? comparator : comparator.reversed();
        TreeBasedTable<Integer, Column, Integer> sortedTable = TreeBasedTable.create(comparator, Ordering.natural());
        sortedTable.putAll(this.table);
        return new AbstractCustomTable(sortedTable, columns);
    }

    public int size() {
        return table.size();
    }

    public String toString() {
        return table.toString();
    }

    public int maxID() {
        return table.rowKeySet().size();
    }

    public Integer[] IDs() {
        return table.rowKeySet().toArray(new Integer[0]);
    }

    public CustomTable copy() {
        TreeBasedTable<Integer, Column, Integer> table = TreeBasedTable.create();
        table.putAll(this.table);
        return new AbstractCustomTable(table, columns);
    }

    public Table<Column, Integer, Integer> transpose() {
        return Tables.transpose(this.table);
    }

    public String toJSON() {
        return toJSON(false);
    }

    public String toJSON(boolean useShortName) {
        StringBuilder output = new StringBuilder("[");
        Integer[] rowIDs = IDs();
        int maxIndex = rowIDs.length - 1;
        for (int i = 0; i < rowIDs.length; i++) {
            StringBuilder rowContent = new StringBuilder("\n    {");
            rowContent.append("\n         \"ID\": ").append(rowIDs[i]);
            Map<Column, Integer> row = row(rowIDs[i]);
            for (Map.Entry<Column, Integer> entry : row.entrySet()) {
                String cellContent = "\n         \"{HEADER}\": {VALUE}";
                String header = (useShortName) ? entry.getKey().toString() : entry.getKey().getLabel();
                cellContent = cellContent
                        .replace("{HEADER}", header)
                        .replace("{VALUE}", entry.getValue().toString());
                rowContent.append(cellContent);
            }
            rowContent.append("\n    }");
            if (i < maxIndex)
                rowContent.append(",");
            output.append(rowContent);
        }
        return output.toString() + "\n]";
    }
}
