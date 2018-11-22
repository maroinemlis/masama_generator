package db.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.bean.Attribute;
import db.bean.Table;
import db.utils.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Control;
import javafx.scene.control.TreeTableView;

/**
 *
 * @author tamac
 */
public class AttributeModel extends RecursiveTreeObject<AttributeModel> {

    private Attribute attribute;
    private JFXCheckBox isPrimaryKey = null;
    private JFXCheckBox isUnique = null;
    private JFXCheckBox isNullable = null;
    private JFXComboBox generatorTypes = null;
    private JFXComboBox specificType = null;
    private Control from;
    private Control to;
    private JFXCheckBox checked;
    private Table table;
    String fromString = "";
    String toString = "";
    String generatorTypeString = "'system";
    String specificTypeString = "'system";

    public void setDisable(boolean selected) {
        if (generatorTypes != null) {
            generatorTypes.setDisable(selected);
        }
        if (specificType != null) {
            specificType.setDisable(selected);
        }
        from.setDisable(selected);
        to.setDisable(selected);
    }

    public AttributeModel(Attribute attribute, Table table) {
        this.table = table;
        this.attribute = attribute;
        checked = new JFXCheckBox();
        fromString = attribute.getDataFaker().getFrom();
        toString = attribute.getDataFaker().getTo();
        checked.selectedProperty().addListener((observable) -> {
            boolean notChecked = !checked.isSelected();
            setDisable(notChecked);
            if (notChecked) {
                System.out.println("now we update");
                update();
                attribute.getDataFaker().setConfiguration(fromString, toString, generatorTypeString, specificTypeString);
            }
        });
        if (attribute.isUnique()) {
            isUnique = new JFXCheckBox();
            isUnique.setDisable(true);
            isUnique.setSelected(true);
        }
        if (attribute.isPrimary()) {
            isPrimaryKey = new JFXCheckBox();
            isPrimaryKey.setDisable(true);
            isPrimaryKey.setSelected(true);
        }
        if (attribute.isNullable()) {
            isNullable = new JFXCheckBox();
            isNullable.setDisable(true);
            isNullable.setSelected(true);
        }

        switch (attribute.getDataType()) {
            case "TEXT":
                from = new JFXSlider(1, 255, Integer.parseInt(fromString));
                to = new JFXSlider(1, 255, Integer.parseInt(toString));
                generatorTypes = new JFXComboBox();
                specificType = new JFXComboBox();
                generatorTypes.getItems().addAll(Types.getInstances().TYPES_MAPPING.keySet());
                generatorTypes.getSelectionModel().selectFirst();
                generatorTypeString = generatorTypes.getItems().get(0).toString();
                specificType.getItems().addAll(Types.getInstances().TYPES_MAPPING.get(generatorTypeString));
                specificTypeString = specificType.getItems().get(0).toString();
                generatorTypes.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                    specificType.getItems().clear();
                    specificType.getItems().addAll(Types.getInstances().TYPES_MAPPING.get(n));
                    specificType.getSelectionModel().selectFirst();
                    generatorTypeString = n.toString();

                });
                specificType.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                    specificTypeString = n.toString();
                });
                break;
            case "DATE":
                this.from = new JFXDatePicker(LocalDate.parse(fromString, DateTimeFormatter.ISO_DATE));
                this.to = new JFXDatePicker(LocalDate.parse(toString, DateTimeFormatter.ISO_DATE));
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                this.from = new JFXTextField(fromString);
                this.to = new JFXTextField(toString);
                break;
        }
        setDisable(true);
    }

    public JFXCheckBox getChecked() {
        return checked;
    }

    public String getName() {
        return attribute.getName();
    }

    public String getDataType() {
        return attribute.getDataType();
    }

    public JFXCheckBox getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public JFXComboBox getGeneratorType() {
        return generatorTypes;
    }

    public JFXComboBox getSpecificType() {
        return specificType;
    }

    public JFXCheckBox getIsUnique() {
        return isUnique;
    }

    public JFXCheckBox getIsNullable() {
        return isNullable;
    }

    public Control getFrom() {
        return from;
    }

    public Control getTo() {
        return to;
    }

    public void update() {
        switch (attribute.getDataType()) {
            case "TEXT":
                fromString = ((int) ((JFXSlider) from).getValue()) + "";
                toString = ((int) ((JFXSlider) to).getValue()) + "";
                break;
            case "DATE":
                fromString = ((JFXDatePicker) from).getValue().toString();
                toString = ((JFXDatePicker) from).getValue().toString();
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                fromString = ((JFXTextField) from).getText();
                toString = ((JFXTextField) to).getText();
                break;
        }

    }

    public Table getTable() {
        return table;
    }
}
