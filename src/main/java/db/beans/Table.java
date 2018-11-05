/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.beans;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * An object represente a table
 *
 * @author Maroine
 */
public final class Table {

    private String tableName;
    private List<Attribute> attributes = new ArrayList();
    private PrimaryKey primaryKey = new PrimaryKey();
    private List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
    private boolean rootTable = true;
    private boolean generated = false;

    public Table(String tableName) throws SQLException {
        this.tableName = tableName;
        fillAttributes();
        fillPrimaryKey();
    }

    /**
     *
     * @return get name of the table
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * add attribute to the table
     *
     * @param attribute
     */
    public void addAttibute(Attribute attribute) {
        attributes.add(attribute);
    }

    /**
     *
     * @param name
     * @return get attribute instance by name
     */
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

    /**
     * Fill all the attributes of the table
     *
     * @throws SQLException
     */
    public void fillAttributes() throws SQLException {
        ResultSet rs = getDatabaseMetaData().getColumns(null, null, tableName, null);
        while (rs.next()) {
            String name = rs.getString("COLUMN_NAME");
            String dataType = rs.getString("TYPE_NAME");
            String nullable = rs.getString("NULLABLE");
            Attribute attribute = new Attribute(name, dataType, nullable);
            attributes.add(attribute);
        }
    }

    /**
     * Fill the primary key of the table
     *
     * @throws SQLException
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
     * Fill the imported ForeignKeys
     *
     * @param schema
     * @throws SQLException
     */
    public void fillForeignKeys(SQLSchema schema) throws SQLException {
        ResultSet rs = getDatabaseMetaData().getImportedKeys(null, null, tableName);
        int foreignKeyNumber = 0;
        while (rs.next()) {
            Attribute fkPart = getAttribute(rs.getString("FKCOLUMN_NAME"));
            fkPart.isRoot(false);
            Table pkTable = schema.getTableByName(rs.getString("PKTABLE_NAME"));
            Attribute pkPart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
            String seq = rs.getString("KEY_SEQ");
            ForeignKey foreignKey = null;
            if (seq.equals("1")) {
                foreignKeyNumber++;
                foreignKey = new ForeignKey(pkTable, foreignKeyNumber);
                foreignKeys.add(foreignKey);
            } else {
                foreignKey = getForeignKeyByNumber(foreignKeyNumber);
            }
            foreignKey.addToTupels(fkPart, pkPart);
        }
    }

    private ForeignKey getForeignKeyByNumber(int foreignKeyNumber) {
        for (ForeignKey foreignKey : foreignKeys) {
            if (foreignKey.getForeignKeyNumber() == foreignKeyNumber) {
                return foreignKey;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String string = "CREATE TABLE " + tableName + " (\n";
        int i = 0;
        for (; i < attributes.size() - 1; i++) {
            string += "\t" + attributes.get(i).toString() + ",\n";
        }
        if (primaryKey.getTuple().isEmpty() && foreignKeys.isEmpty()) {
            string += "\t" + attributes.get(i).toString() + "\n";
        } else {
            string += "\t" + attributes.get(i).toString() + ",\n";
            if (foreignKeys.isEmpty()) {
                string += "\t" + primaryKey.toString() + "\n";
            } else {
                int j = 0;
                for (; j < foreignKeys.size() - 1; j++) {
                    string += "\t" + foreignKeys.get(j).toString() + ",\n";
                }
                string += "\t" + foreignKeys.get(j).toString() + "\n";
            }
        }

        return string + ");";

    }

    /**
     * Generates instances exemples for each attribtute
     *
     * @throws ParseException
     */
    public void startToGenerateInstances() throws ParseException {
        for (Attribute a : attributes) {
            a.startToGenerateRootValues();
        }
        for (ForeignKey foreignKey : foreignKeys) {
            List<Attribute> fkTuple = foreignKey.getFkTuple();
            List<Attribute> pkTuple = foreignKey.getPkTuple();
            for (int i = 0; i < pkTuple.size(); i++) {
                fkTuple.get(i).setInstances(pkTuple.get(i).getInstances());
            }
        }
    }

    /**
     * Shows instances exemples for each attribtute
     */
    public void show() {
        int rowNumber = attributes.get(0).getInstances().size();
        for (int j = 0; j < rowNumber - 1; j++) {
            String insert = "INSERT INTO " + tableName + " VALUES (";
            int i = 0;
            Attribute a = null;
            for (; i < attributes.size() - 1; i++) {
                a = attributes.get(i);
                insert += a.getInstances().get(j) + ", ";
            }
            a = attributes.get(i);
            insert += a.getInstances().get(j) + ");";
            System.out.println(insert);
        }

    }

}
