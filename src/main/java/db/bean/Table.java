/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import db.connection.SQLConnection;
import static db.connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import views.alertMeg.AlertExeption;

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
    private boolean isErrorInTable = false;
    private boolean isTakePreData = false;
    List<String> listTable = new ArrayList();

    public boolean getIsErrorInTable() {
        return isErrorInTable;
    }

    public boolean getIsIsTakePreData() {
        return isTakePreData;
    }

    public void setIsIsTakePreData(boolean isTakePreData, SQLConnection connection) throws SQLException {
        this.isTakePreData = isTakePreData;
        for (Attribute attribute : attributes) {
            attribute.setIsIsTakePreData(isTakePreData);
            List<String> listPreInstances = connection.executeQuerySelecte(this.getTableName(), attribute.getName());
            attribute.setPreInstances(listPreInstances);
        }
    }

    /**
     * get the number of ligne to be generated
     *
     * @return int
     */
    public int getHowMuch() {
        return howMuch;
    }

    /**
     * set the number of ligne to be generated
     *
     * @param int
     */
    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
        attributes.forEach(a -> a.getDataFaker().setHowMuch(howMuch));
    }

    /**
     * Construcor for class Tabel
     *
     * set table name, set number of ligne to be generated
     *
     * fill attributes and primary key of the table
     *
     * @throws Exception
     * @param tableName
     */
    public Table(String tableName) throws Exception {
        this.tableName = tableName;
        this.howMuch = 10;
        fillAttributes();
        fillPrimaryKey();
    }

    /**
     * get name of the table
     *
     * @return String
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * add attribute to the table
     *
     * @param Attribute
     */
    public void addAttibute(Attribute attribute) {
        attributes.add(attribute);
    }

    /**
     * return the list attributes of a table
     *
     * @return List<Attribute>
     */
    public List<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * return an attribute by its name
     *
     * @param name
     * @return Attribute
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
     * Fill the attributes of the table
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
            attribute.getDataFaker().setHowMuch(howMuch);
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
    public void fillForeignKeys(SQLSchema schema) {
        try {
            ResultSet rs = getDatabaseMetaData().getImportedKeys(null, null, tableName);
            while (rs.next()) {
                Attribute fkTuplePart = getAttribute(rs.getString("FKCOLUMN_NAME"));
                Table pkTable = schema.getTable(rs.getString("PKTABLE_NAME"));
                Attribute pkTuplePart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
                pkTuplePart.getReferencesMe().add(fkTuplePart);
                fkTuplePart.getReferences().add(pkTuplePart);
            }
        } catch (Exception ex) {
            isErrorInTable = true;
            System.out.println("db.bean.Table.fillForeignKeys()");
            String msg = ex.getMessage();

            if (msg == null) {
                msg = "Error in SQL File";
            }
            AlertExeption alertExeption = new AlertExeption(msg);
            alertExeption.showStandarAlert();

        }
    }

    /**
     * Generates instances exemples for each attribtute
     *
     */
    public void startToGenerateInstances() {
        attributes.forEach(a -> {
            if (a.getReferences().isEmpty()) {
                a.startToGenerateRootValues();
            }
        });
    }

    public void fixAttributesInstances() {
        attributes.forEach(Attribute::fixInstancesHowMuch);
    }

    public void show() {
        System.out.println(tableName + "----------------------");
        attributes.forEach(a -> {
            System.out.println(a.getName() + " : " + a.getInstances());
        });

    }
}
