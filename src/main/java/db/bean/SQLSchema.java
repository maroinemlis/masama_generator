package db.bean;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.sql.*;
import java.text.ParseException;
import java.util.*;

public final class SQLSchema {

    private List<Table> tables = new ArrayList<>();
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
    private void GenerateTables() throws SQLException {
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
    private void fillForeignKeysForTables() throws SQLException {
        for (Table table : tables) {
            table.fillForeignKeys(this);
        }
    }

    public void generateInstances() throws ParseException {
        for (Table table : tables) {
            table.startToGenerateInstances();
        }
    }

    public void show() throws ParseException {
        for (Table table : tables) {
            table.show();
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

}
