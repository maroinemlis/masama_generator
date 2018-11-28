/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * An object represente a table
 *
 * @author Maroine
 */
public final class Table implements Serializable, Comparable<Table> {

    private String tableName;
    private List<Attribute> attributes = new LinkedList();
    private PrimaryKey primaryKey = new PrimaryKey();
    private List<ForeignKey> foreignKeys = new LinkedList<>();
    private int howMuch;

    public int getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    public Table(String tableName) throws Exception {
        this.tableName = tableName;
        this.howMuch = 10;
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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
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
    public void fillAttributes() throws Exception {
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
    public void fillPrimaryKey() throws Exception {
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
    public void fillForeignKeys(SQLSchema schema) throws Exception {
        ResultSet rs = getDatabaseMetaData().getImportedKeys(null, null, tableName);
        int foreignKeyNumber = 0;
        while (rs.next()) {

            Attribute fkTuplePart = getAttribute(rs.getString("FKCOLUMN_NAME"));

            Table pkTable = schema.getTableByName(rs.getString("PKTABLE_NAME"));
            Attribute pkTuplePart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
            String seq = rs.getString("KEY_SEQ");
            ForeignKey foreignKey = null;
            if (seq.equals("1")) {
                foreignKeyNumber++;
                foreignKey = new ForeignKey(pkTable, foreignKeyNumber);
                foreignKeys.add(foreignKey);
            } else {
                foreignKey = getForeignKeyByNumber(foreignKeyNumber);
            }
            //fkTuplePart.setRef(pkTuplePart);
            foreignKey.addToTupels(fkTuplePart, pkTuplePart);

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

    /**
     * Generates instances exemples for each attribtute
     *
     */
    public void startToGenerateInstances() {
        for (Attribute a : attributes) {
            a.startToGenerateRootValues(this.howMuch);
        }
        for (ForeignKey fk : foreignKeys) {
            List<Attribute> fkTuple = fk.getFkTuple();
            List<Attribute> pkTuple = fk.getPkTuple();

            for (int i = 0; i < pkTuple.size(); i++) {
                if (pkTuple.get(i).getInstances() != null) {
                    fkTuple.get(i).setInstances(getListForeigKey(fkTuple, pkTuple, i));
                }
            }
        }

    }

    private List<String> getListForeigKey(List<Attribute> fkTuple, List<Attribute> pkTuple, int i) {
        int fkHowMuch = fkTuple.get(i).getDataFaker().getHowMuch();
        int pkHowMuch = pkTuple.get(i).getDataFaker().getHowMuch();
        int mDiv = fkHowMuch / pkHowMuch;
        int mMod = fkHowMuch % pkHowMuch;

        int size = pkTuple.get(i).getInstances().size();

        //System.err.println(pkTuple.get(i).getInstances());
        List<String> list = new ArrayList<>();
        for (int j = 0; j < mDiv; j++) {
            List<String> a = pkTuple.get(i).getInstances().subList(0, size);
            list.addAll(a);
        }
        List<String> a = pkTuple.get(i).getInstances().subList(0, mMod);
        list.addAll(a);
        return list;
    }

    /**
     * Shows instances exemples for each attribtute
     */
    public void show() {
        System.out.println("-------------------------" + this.getTableName());
        int rowNumber = attributes.get(0).getInstances().size();
        for (int j = 0; j < rowNumber; j++) {
            String insert = "INSERT INTO " + this.getTableName() + " VALUES (";
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

    List<String> listTable = new ArrayList();

    @Override
    public int compareTo(Table table) {
        int result;
        if (table.getTableName().equals(this.getTableName())) {
            result = 0;
        }
        result = isParentOfthisTable(table);
        return result;
    }

    private int isParentOfthisTable(Table table) {
        if (!listTable.contains(table.getTableName())) {
            listTable.add(table.getTableName());
            if (!table.getForeignKeys().isEmpty()) {
                result = isCirculedInTable(table.getForeignKeys().get(0).getReferences());
            }
        } else {
            result = true;
        }
    }

}
