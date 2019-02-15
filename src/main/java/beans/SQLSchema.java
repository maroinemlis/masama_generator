package beans;

import static connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import controllers.tableview.TableView;
import java.util.logging.Level;
import java.util.logging.Logger;
import pre_condition.PreCondition;

public class SQLSchema implements Serializable {

    private List<Table> tables = new ArrayList<>();
    private String name;//--unused
    private boolean preData = false;
    private static SQLSchema singletonSQLSchema = null;
    private ArrayList<Attribute> sortedRootAttributes = new ArrayList<>();
    private long generationTime = 0;

    /**
     * Get the time taken in generation of data in millisecond
     *
     * @return
     */
    public long getGenerationTime() {
        return generationTime;
    }

    /**
     * Return a list of attributes which algorithm has not yet generated values
     *
     * @return
     */
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

    /**
     * Fill the list sortedRootAttributes with all attributes that there are no
     * references, sorted by the number of row to generate
     */
    private void generateSortedRootAttributes() {
        for (Table t : tables) {
            for (Attribute a : t.getAttributes()) {
                if (a.getReferences().isEmpty()) {
                    sortedRootAttributes.add(a);
                }
            }
        }
        if (sortedRootAttributes.size() >= 2) {
            sortedRootAttributes.sort((a1, a2) -> Integer.compare(a1.getTable().getHowMuch(), a2.getTable().getHowMuch()));

        }
    }

    /**
     * Initializes a newly created SQLSchema if it is not already initialized.
     *
     * @return the newly instance if singletonSQLSchema is null else return the
     * old instance.
     */
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

    /**
     * Sets the singletonSQLSchema instance of SQLSchema.
     *
     * @param singletonSQLSchema
     */
    public static void setInstance(SQLSchema singletonSQLSchema) {
        SQLSchema.singletonSQLSchema = singletonSQLSchema;
    }

    /**
     * Call the method GenerateTables() to generate all tables, then for all
     * attribute call method fillForeignKeys() to generate references and
     * referanceMe and checkCirculairAttribute() to indicate if the attribute
     * inside circular schema, at last call the method
     * generateSortedRootAttributes()
     *
     * @exception SQLException
     */
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

    /**
     * --unused
     *
     * @return
     */
    public boolean isPreData() {
        return preData;
    }

    /**
     * --unused
     *
     * @param preData
     */
    public void isPreData(boolean preData) {
        this.preData = preData;
    }

    /**
     * Return all Tables of SQLSchema inside List
     *
     * @return list of Table
     */
    public List<Table> getTables() {
        return tables;
    }

    /**
     * Get the table by name
     *
     * @param tableName the name of Table to return
     * @return Table with the name is tableName
     */
    public Table getTable(String tableName) {
        return tables.stream().filter(t -> t.getTableName().equals(tableName)).findFirst().get();
    }

    /**
     * --unused
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Generates tables instances for the SQLSchema
     *
     * @throws SQLException
     */
    private void GenerateTables() throws SQLException {
        ResultSet rs = getDatabaseMetaData().getTables(null, null, "%", null);
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            Table table = new Table(tableName);
            tables.add(table);
            //System.out.println(tableName);
        }
    }

    /**
     * Reset the all instances inside SQLSchema
     */
    public void resetGeneration() {
        tables.forEach(t -> t.getAttributes().forEach(a -> {
            a.getInstances().clear();
            a.isGenerated(false);
        }));
        /* do this insted of the code in top
        tables.forEach(t -> {
            t.getAttributes().forEach(a -> {
                a.getInstances().clear();
                a.isGenerated(false);
            });
            t.getAttributes().clear();
        });
        tables.clear();
         */
    }

    /**
     * Return List of instances for all tables
     *
     * @return
     */
    public List<TableView> getTablesAsTablesView() {
        return tables.stream().map(t -> new TableView(t)).collect(Collectors.toList());
    }

    /**
     * generate values of all attribute. first case is to generate values of
     * attributes there have no references, after that generating to attributes
     * that do not have yet values
     *
     * @throws Exception if Connection not established or one of the pre
     * condition is false
     */
    public void startToGenerateInstances() throws Exception {
        if (SQLSchema.getInstance().getTables().isEmpty()) {
            throw new Exception("Connexion non établie");
        }
        if (PreCondition.getInstance().checkSQLSchema()) {
            throw new Exception(PreCondition.getInstance().getErrorMessage());
        }

        long start = System.currentTimeMillis();
        resetGeneration();
        generateCaseOf(sortedRootAttributes);
        generateCaseOf(getEmptyAttributes());
        tables.forEach(t -> t.fixInstances());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        generationTime = end - start;
    }

    /**
     * Generate values of list passed in parameters. for all attributes generate
     * them their value, generate value of all attribute referenced by this
     * attribute and attributes how reference to this attribute.
     *
     * @param attributes
     */
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

    /**
     * --unused
     */
    public void clear() {
    }
}
