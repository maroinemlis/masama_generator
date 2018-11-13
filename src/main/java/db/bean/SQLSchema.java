package db.bean;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import views.main.TableView;

public final class SQLSchema {

    private List<Table> tables = new LinkedList<>();
    private String name;

    public List<Table> getTables() {
        return tables;
    }

    public Table getTable(String tableName) {
        for (Table t : tables) {
            if (t.getTableName().equals(tableName)) {
                return t;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    /**
     *
     * @throws Exception
     */
    public SQLSchema() throws Exception {
        GenerateTables();
        fillForeignKeysForTables();
    }

    /**
     * Generates tables instances for the current SQLSchema
     *
     * @throws SQLException
     */
    private void GenerateTables() throws Exception {
        ResultSet rs = getDatabaseMetaData().getTables(null, null, "%", null);
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            Table table = new Table(tableName);
            tables.add(table);
        }
    }

    public void showSQLSchema() {
        for (Table table : tables) {
            System.out.println(table);
        }
    }

    /**
     * Fill the imported ForeignKeys for each table
     *
     * @throws SQLException
     */
    private void fillForeignKeysForTables() throws Exception {
        for (Table table : tables) {
            table.fillForeignKeys(this);
        }
    }

    /**
     * search table instance by its name
     *
     * @param tableName
     * @return instance of the table searched
     */
    public Table getTableByName(String tableName) {
        for (Table table : tables) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    public void startToGenerateInstances() {
        for (Table t : tables) {
            t.startToGenerateInstances();
        }
    }

    public List<TableView> getTablesAsTablesView() {
        return tables.stream().map(t -> new TableView(t)).collect(Collectors.toList());
    }
}
