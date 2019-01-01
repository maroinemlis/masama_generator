package db.bean;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import views.main.TableView;

public final class SQLSchema implements Serializable {

    private List<Table> tables = new ArrayList<>();
    private String name;

    /**
     * Get the list of table schema
     *
     * @return List<Table>
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * Get the table by name
     *
     * @param tableName
     * @return Table
     */
    public Table getTable(String tableName) {
        return tables.stream().filter(t -> t.getTableName().equals(tableName)).findFirst().get();
    }

    /**
     * Get the name of table
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Construcor for class SQLSchema, Generate Tables and fill their foreinkeys
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

    /**
     * Fill the imported ForeignKeys for each table
     *
     * @throws SQLException
     */
    private void fillForeignKeysForTables() throws Exception {
        tables.forEach(t -> t.fillForeignKeys(this));
    }

    /**
     * generate instances
     */
    public void startToGenerateInstances() {
        clearInstances();
        tables.forEach(Table::startToGenerateInstances);
        tables.forEach(Table::fixAttributesInstances);
        //tables.forEach(Table::show);
    }

    public void clearInstances() {
        tables.forEach(t -> t.getAttributes().forEach(a -> a.getInstances().clear()));

    }

    /**
     * get tables as tables view
     *
     * @return List<TableView>
     */
    public List<TableView> getTablesAsTablesView() {
        return tables.stream().map(t -> new TableView(t)).collect(Collectors.toList());
    }
}
