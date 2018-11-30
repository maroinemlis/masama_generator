/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import static db.connection.SQLConnection.getDatabaseMetaData;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import views.alertMeg.AlertExeption;

/**
 * An object represente a table
 *
 * @author Maroine
 */
public final class Table implements Serializable {

    private String tableName;
    private List<Attribute> attributes = new LinkedList();
    private PrimaryKey primaryKey = new PrimaryKey();
    private int howMuch;
    private boolean isErrorInTable = false;

    public boolean getIsErrorInTable() {
        return isErrorInTable;
    }

    public int getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
        attributes.forEach(a -> a.getDataFaker().setHowMuch(howMuch));
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
                Table pkTable = schema.getTableByName(rs.getString("PKTABLE_NAME"));
                Attribute pkTuplePart = pkTable.getAttribute(rs.getString("PKCOLUMN_NAME"));
                fkTuplePart.setReference(pkTuplePart);
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

        System.out.println(tableName);
        for (Attribute a : attributes) {
            if (a.getReference() == null) {
                a.startToGenerateRootValues();
            }
        }
        generateIdentiqueInTupleIfExist();
    }

    public void generateIdentiqueInTupleIfExist() {
        List<Attribute> list = eachTupleSholdBeIdentique();
        int i = 0;
        Attribute tempAttribute = null;
        for (Attribute attribute : list) {
            if (i != 0) {
                attribute.setInstances(tempAttribute.getInstances());
            }
            tempAttribute = attribute;
            i++;
        }
    }

    /**
     * return List of
     *
     * @return List<Attribute>
     */
    private List<Attribute> eachTupleSholdBeIdentique() {

        List<Attribute> l = new ArrayList<>();
        for (Attribute attribute : this.getAttributes()) {
            try {
                l = new ArrayList<>();
                for (Attribute attributeReferenceMe : attribute.getReferencesMe()) {
                    if (l.contains(attributeReferenceMe) || l.isEmpty()) {
                        l.add(attributeReferenceMe);
                    } else {
                        l.clear();
                        for (Attribute attribute1 : this.getAttributes()) {
                            if (attribute1.isPrimary()) {
                                l.add(attribute1);
                            }
                        }
                        return l;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("db.validation.PreCondetion.checkIfReferenceExist()");
            }
        }
        l.clear();
        return l;

    }

    public void startToGenerateInstancesForForeignKey() {
        for (Attribute a : attributes) {
            if (a.getInstances().isEmpty() && a.getReference() != null) {
                a.getInstances().addAll(getListForeigKey(a, a.getReference()));
            }
        }
    }

    private List<String> getListForeigKey(Attribute fkPart, Attribute pkPart) {
        int fkHowMuch = fkPart.getDataFaker().getHowMuch();
        int pkHowMuch = pkPart.getDataFaker().getHowMuch();
        int mDiv = fkHowMuch / pkHowMuch;
        int mMod = fkHowMuch % pkHowMuch;
        int size = pkPart.getInstances().size();
        List<String> list = new ArrayList<>();
        for (int j = 0; j < mDiv; j++) {
            list.addAll(pkPart.getInstances());
            fkHowMuch = fkHowMuch - size;
        }
        System.out.println(fkHowMuch);
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

}
