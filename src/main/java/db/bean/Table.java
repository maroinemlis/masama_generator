/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import static db.connection.SQLConnection.getDatabaseMetaData;
import db.models.AttributeModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

/**
 * An object represente a table
 *
 * @author Maroine
 */
public final class Table {

    private String tableName;
    private List<Attribute> attributes = new ArrayList();
    private PrimaryKey primaryKey = new PrimaryKey();
    private List<ForeignKey> foreignKeys = new ArrayList<>();
    private boolean rootTable = true;
    private boolean generated = false;
    private int howMuch;
    private int nullsRate;

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

    public JFXTreeTableView<AttributeModel> createInsertsForTable() {
        JFXTreeTableView<AttributeModel> tableView = new JFXTreeTableView<>();
        tableView.setShowRoot(false);
        JFXTreeTableColumn<AttributeModel, String> col = new JFXTreeTableColumn<>("att");
        tableView.getColumns().add(col);

        Attribute a = this.getAttributes().get(0);
        col.setCellValueFactory(new TreeItemPropertyValueFactory("value"));
        List<AttributeModel> collect = a.getInstances().stream().
                map(str -> new AttributeModel(a)).
                collect(Collectors.toList());
        System.out.println(a.getInstances());
        ObservableList<AttributeModel> observables = FXCollections.observableArrayList(collect);
        tableView.setRoot(new RecursiveTreeItem<>(observables, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        return tableView;
    }

    public JFXTreeTableView<AttributeModel> createTableView() {
        JFXTreeTableView<AttributeModel> treeTableView = new JFXTreeTableView<>();
        treeTableView.setShowRoot(false);
        JFXTreeTableColumn<AttributeModel, String> name = new JFXTreeTableColumn<>("Colonne");
        JFXTreeTableColumn<AttributeModel, String> type = new JFXTreeTableColumn<>("Type");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> pk = new JFXTreeTableColumn<>("Clé primaire");
        JFXTreeTableColumn<AttributeModel, String> generatorType = new JFXTreeTableColumn<>("Type de données");
        JFXTreeTableColumn<AttributeModel, String> spesifcGeneratorType = new JFXTreeTableColumn<>("Spécifique");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> unique = new JFXTreeTableColumn<>("Unique");
        JFXTreeTableColumn<AttributeModel, JFXCheckBox> nullable = new JFXTreeTableColumn<>("Nullable");

        name.setCellValueFactory(new TreeItemPropertyValueFactory("name"));
        type.setCellValueFactory(new TreeItemPropertyValueFactory("dataType"));
        pk.setCellValueFactory(new TreeItemPropertyValueFactory("isPrimaryKey"));
        generatorType.setCellValueFactory(new TreeItemPropertyValueFactory("generatorDataType"));
        spesifcGeneratorType.setCellValueFactory(new TreeItemPropertyValueFactory("specificType"));
        unique.setCellValueFactory(new TreeItemPropertyValueFactory("isUnique"));
        nullable.setCellValueFactory(new TreeItemPropertyValueFactory("isNullable"));

        List<AttributeModel> collect = this.getAttributes().stream().
                map(att -> new AttributeModel(att)).
                collect(Collectors.toList());
        ObservableList<AttributeModel> observables = FXCollections.observableArrayList(collect);
        treeTableView.getColumns().addAll(name, type, pk, generatorType, spesifcGeneratorType, unique, nullable);
        treeTableView.setRoot(new RecursiveTreeItem<>(observables, (recursiveTreeObject) -> recursiveTreeObject.getChildren()));
        return treeTableView;
    }

    /**
     * Generates instances exemples for each attribtute
     *
     * @throws ParseException
     */
    public void startToGenerateInstances() throws ParseException {
        for (Attribute a : this.getAttributes()) {
            a.startToGenerateRootValues(this.howMuch, this.nullsRate);
        }
        for (ForeignKey foreignKey : this.getForeignKeys()) {
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
        List<Attribute> attributes = this.getAttributes();
        int rowNumber = attributes.get(0).getInstances().size();
        for (int j = 0; j < rowNumber - 1; j++) {
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

}
