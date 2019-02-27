/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An object represent a table for an SQL schema.
 *
 * @author Maroine
 */
public final class Table implements Serializable {

    private String tableName;
    private List<Attribute> attributes = new ArrayList<>();
    private PrimaryKey primaryKey = new PrimaryKey();
    private int howMuch;
    private double nullsRate;

    /**
     * Return number of row to generate for all attributes of Table
     *
     * @return number of row to generate
     */
    public int getHowMuch() {
        return howMuch;
    }

    /**
     * Return percentage of null values to generate
     *
     * @return percentage of null values
     */
    public double getNullsRate() {
        return nullsRate;
    }

    /**
     * Set the number of how much row to generate
     *
     * @param howMuch the number of row to generate
     */
    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    /**
     * Set the percentage of null value to generate in the Table
     *
     * @param nullsRate
     */
    public void setNullsRate(double nullsRate) {
        this.nullsRate = nullsRate;
    }

    /**
     * Initializes a newly name of table and how much row, and call to
     * fillAttributes() and fillPrimaryKey() methods to construct the attributes
     * and primaryKey of Table
     *
     * @param tableName
     * @throws SQLException if a database access error occurs
     */
    public Table(String tableName) throws SQLException {
        this.tableName = tableName;
        this.howMuch = 10;
        fillAttributes();
        fillPrimaryKey();
    }

    /**
     * Get the name of Table
     *
     * @return the name of Table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Get List of Attributes. All attribute of specific Table
     *
     * @return List of Attribute of this Table
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Get the instance of Attribute by name. the method return null if the
     * their no attribute withe this name
     *
     * @param name the name of attribute
     * @return instance of Attribute withe the name is the value of variable
     * name
     */
    public Attribute getAttribute(String name) {
        for (Attribute a : attributes) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Get List of Attributes index. All index of specific Table
     *
     * @return List of Index of this Table
     */
    public List<Attribute> getIndex() {
        return attributes.stream().filter(a -> a.isIndex()).collect(Collectors.toList());
    }

    /**
     * Create instances of all attributes of the Table. fill the list attributes
     * of Table with all his attribute. For each attribute specify his name,
     * type and if nullable
     *
     * @throws SQLException if a database access error occurs
     */
    public void fillAttributes() throws SQLException {
        ResultSet rs = getDatabaseMetaData().getColumns(null, null, tableName, null);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            String dataType = rs.getString("TYPE_NAME");
            String nullable = rs.getString("NULLABLE");
            Attribute attribute = new Attribute(this, name, dataType, nullable);
            //Attribute a = getAttribute(name);
            attributes.add(attribute);
        }
    }

    /**
     * Indicate each attribute is Primary key. Set the parameter isPrimary of
     * Attribute true for all primary key and make all Primary key (attribute)
     * in list PrimaryKey
     *
     * @throws SQLException if a database access error occurs
     */
    public void fillPrimaryKey() throws SQLException {
        ResultSet rs = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            Attribute attribute = getAttribute(name);
            attribute.isPrimary(true);
            primaryKey.addToPrimaryKey(attribute);
        }
    }

    /**
     *
     * @throws SQLException if a database access error occurs or this method is
     * called on a closed result set
     */
    public void fillForeignKeys() throws SQLException {
        ResultSet rs = getDatabaseMetaData().getImportedKeys(null, null, tableName);
        while (rs.next()) {
            Attribute fkTuplePart = getAttribute(rs.getString("FKCOLUMN_NAME"));
            Table pkTable = SQLSchema.getInstance().getTable(rs.getString("PKTABLE_NAME"));
            Attribute pkTuplePart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
            pkTuplePart.getReferencesMe().add(fkTuplePart);
            fkTuplePart.getReferences().add(pkTuplePart);
        }

    }

    /**
     * For each attribute in Table call the method testIfCircular(). this method
     * indicate if attribute is in circular schema
     */
    public void checkCirculairAttribute() {
        attributes.forEach(a -> a.testIfCircular(new ArrayList<Attribute>(), a));
    }

    /**
     * For each attribute in Table call the method testIfCircular(). this method
     * ensures if that the number of value generated for the attribute is the
     * one requested
     */
    public void fixInstances() {
        attributes.forEach(a -> a.fixInstances());
    }

    /**
     * Print in the terminal for each attribute in this table his name and his
     * values
     */
    public void show() {
        attributes.forEach(a -> {
            System.out.println(a.getName() + " : " + a.getInstances());
        });
    }

}
