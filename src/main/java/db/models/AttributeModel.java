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
import db.utils.Types;
import javafx.scene.control.Control;

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

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModel(Attribute attribute) {
        this.attribute = attribute;
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
                this.from = new JFXSlider(1, 255, 20);
                this.to = new JFXSlider(1, 255, 20);
                generatorTypes = new JFXComboBox();
                specificType = new JFXComboBox();
                generatorTypes.getItems().addAll(Types.getInstances().TYPES_MAPPING.keySet());
                generatorTypes.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
                    specificType.getItems().clear();
                    specificType.getItems().addAll(Types.getInstances().TYPES_MAPPING.get(n));
                });
                break;
            case "DATE":
                this.from = new JFXDatePicker();
                this.to = new JFXDatePicker();

                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":

                this.from = new JFXTextField();
                this.to = new JFXTextField();
                break;
        }
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

    public String toString() {
        return this.attribute.toString();
    }
}
