/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import static connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * An object represent a table
 *
 * @author Maroine
 */
public final class Table implements Serializable {

    private String tableName;
    private List<Attribute> attributes = new ArrayList<>();
    private PrimaryKey primaryKey = new PrimaryKey();
    private int howMuch;
    private double nullsRate;

    public int getHowMuch() {
        return howMuch;
    }

    public double getNullsRate() {
        return nullsRate;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    public void setNullsRate(double nullsRate) {
        this.nullsRate = nullsRate;
    }

    public Table(String tableName) throws Exception {
        this.tableName = tableName;
        this.howMuch = 10;
        fillAttributes();
        fillPrimaryKey();
    }

    public String getTableName() {
        return tableName;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public Attribute getAttribute(String name) {
        for (Attribute a : attributes) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public void fillAttributes() throws Exception {
        ResultSet rs = getDatabaseMetaData().getColumns(null, null, tableName, null);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            String dataType = rs.getString("TYPE_NAME");
            String nullable = rs.getString("NULLABLE");
            Attribute attribute = new Attribute(this, name, dataType, nullable);
            Attribute a = getAttribute(name);
            attributes.add(attribute);
        }
    }

    public void fillPrimaryKey() throws Exception {
        ResultSet rs = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            Attribute attribute = getAttribute(name);
            attribute.isPrimary(true);
            primaryKey.addToPrimaryKey(attribute);
        }
    }

    public void fillForeignKeys() throws Exception {
        ResultSet rs = getDatabaseMetaData().getImportedKeys(null, null, tableName);
        while (rs.next()) {
            Attribute fkTuplePart = getAttribute(rs.getString("FKCOLUMN_NAME"));
            Table pkTable = SQLSchema.getInstance().getTable(rs.getString("PKTABLE_NAME"));
            Attribute pkTuplePart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
            pkTuplePart.getReferencesMe().add(fkTuplePart);
            fkTuplePart.getReferences().add(pkTuplePart);
        }

    }

    public void checkCirculairAttribute() {
        attributes.forEach(a -> a.testIfCircular(new ArrayList<Attribute>(), a));
    }

    public void fixInstances() {
        attributes.forEach(a -> a.fixInstances());
    }

    public void show() {
        attributes.forEach(a -> {
            System.out.println(a.getName() + " : " + a.getInstances());
        });
    }

}
