package db.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.bean.Attribute;

/**
 *
 * @author tamac
 */
public class AttributeModel extends RecursiveTreeObject<AttributeModel> {

    private Attribute attribute;

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeModel(Attribute attribute) {
        this.attribute = attribute;

    }

    public String getName() {
        return attribute.getName();
    }

    public String getDataType() {
        return attribute.getDataType();
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

    public String getGeneratorType() {
        return attribute.getDataFaker().getGeneratorType();
    }

    public String getSpecificType() {
        return attribute.getDataFaker().getSpecificType();
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

    public void setDataType(String type) {
        this.attribute.setDataType(type);
    }

    public String toString() {
        return this.attribute.toString();
    }
}
