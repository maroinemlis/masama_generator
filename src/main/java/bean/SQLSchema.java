package bean;

import static connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import controllers.tableview.TableView;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLSchema implements Serializable {

    private List<Table> tables = new ArrayList<>();
    private String name;
    private boolean preData = false;
    private static SQLSchema singletonSQLSchema = null;
    private ArrayList<Attribute> sortedRootAttributes = new ArrayList<>();
    private long generationTime = 0;

    public long getGenerationTime() {
        return generationTime;
    }

    private List<Attribute> getEmptyAttributes() {
        ArrayList<Attribute> emptyAttribute = new ArrayList<>();
        for (Table t : tables) {
            for (Attribute a : t.getAttributes()) {
                if (!a.isGenerated()) {
                    emptyAttribute.add(a);
                }
            }
        }
        emptyAttribute.sort((a1, a2) -> Integer.compare(a1.getTable().getHowMuch(), a2.getTable().getHowMuch()));
        return emptyAttribute;
    }

    private void generateSortedRootAttributes() {
        for (Table t : tables) {
            for (Attribute a : t.getAttributes()) {
                if (a.getReferences().isEmpty()) {
                    sortedRootAttributes.add(a);
                }
            }
        }
        sortedRootAttributes.sort((a1, a2) -> Integer.compare(a1.getTable().getHowMuch(), a2.getTable().getHowMuch()));
    }

    public static SQLSchema getInstance() {
        if (singletonSQLSchema == null) {
            try {
                singletonSQLSchema = new SQLSchema();
            } catch (Exception ex) {
                Logger.getLogger(SQLSchema.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return singletonSQLSchema;
    }

    public static void setInstance(SQLSchema singletonSQLSchema) {
        SQLSchema.singletonSQLSchema = singletonSQLSchema;
    }

    public void constructSchema() throws Exception {
        GenerateTables();
        for (Table t : tables) {
            t.fillForeignKeys();
        }
        for (Table t : tables) {
            t.checkCirculairAttribute();
        }
        generateSortedRootAttributes();
    }

    public boolean isPreData() {
        return preData;
    }

    public void isPreData(boolean preData) {
        this.preData = preData;
    }

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

    public void resetGeneration() {
        tables.forEach(t -> t.getAttributes().forEach(a -> {
            a.getInstances().clear();
            a.isGenerated(false);
        }));
    }

    public List<TableView> getTablesAsTablesView() {
        return tables.stream().map(t -> new TableView(t)).collect(Collectors.toList());
    }

    public void startToGenerateInstances() {
        long start = System.currentTimeMillis();
        resetGeneration();
        generateCaseOf(sortedRootAttributes);
        generateCaseOf(getEmptyAttributes());
        tables.forEach(t -> t.fixInstances());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        generationTime = end - start;
    }

    public void generateCaseOf(List<Attribute> attributes) {
        for (Attribute a : attributes) {
            if (!a.isGenerated()) {
                a.startToGenerateRootValues();
                a.startToGenerateWhoReference();
                a.startToGenerateWhoReferenceMe();
                a.isGenerated(true);
            }
        }
    }
}
