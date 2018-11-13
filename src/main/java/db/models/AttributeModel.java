package db.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
    private JFXComboBox generatorTypes = new JFXComboBox();
    private JFXComboBox specificType = new JFXComboBox();

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModel(Attribute attribute) {
        this.attribute = attribute;
        generatorTypes.getItems().addAll(Types.getInstances().TYPES_MAPPING.keySet());
        generatorTypes.getSelectionModel().selectedItemProperty().addListener((ob, o, n) -> {
            specificType.getItems().clear();
            specificType.getItems().addAll(Types.getInstances().TYPES_MAPPING.get(n));
        });
    }

    public String getName() {
        return attribute.getName();
    }

    public String getDataType() {
        return attribute.getDataType();
    }

    public JFXCheckBox getIsSelected() {
        JFXCheckBox JFXCheckBox = new JFXCheckBox();
        return JFXCheckBox;
    }

    public JFXCheckBox getIsPrimaryKey() {
        if (attribute.isPrimary()) {
            JFXCheckBox JFXCheckBox = new JFXCheckBox();
            JFXCheckBox.setDisable(true);
            JFXCheckBox.setSelected(true);
            return JFXCheckBox;
        }
        return null;
    }

    public JFXComboBox getGeneratorType() {
        return generatorTypes;
        // return attribute.getDataFaker().getGeneratorType();
    }

    public JFXComboBox getSpecificType() {
        return specificType;
        //return attribute.getDataFaker().getSpecificType();
    }

    public JFXCheckBox getIsUnique() {
        if (attribute.isUnique()) {
            JFXCheckBox JFXCheckBox = new JFXCheckBox();
            JFXCheckBox.setDisable(true);
            JFXCheckBox.setSelected(true);
            return JFXCheckBox;
        }
        return null;
    }

    public JFXCheckBox getIsNullable() {
        if (attribute.isNullable()) {
            JFXCheckBox JFXCheckBox = new JFXCheckBox();
            JFXCheckBox.setDisable(true);
            JFXCheckBox.setSelected(true);
            return JFXCheckBox;
        }
        return null;
    }

    public Control getFrom() {
        return attribute.getFromControl();
    }

    public Control getTo() {
        return attribute.getToControl();
    }

    public String toString() {
        return this.attribute.toString();
    }
}
