package db.models;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.github.javafaker.Faker;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.bean.Attribute;
import db.utils.DataFaker;
import db.utils.DateDataFaker;
import db.utils.IntegerDataFaker;
import db.utils.TextDataFaker;

/**
 *
 * @author tamac
 */
public class AttributeModel extends RecursiveTreeObject<AttributeModel> {

    private Attribute attribute;

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

    public boolean isUnique() {
        return attribute.getDataFaker().isUnique();
    }

    public String getFrom() {
        return attribute.getDataFaker().getFrom();
    }

    public String getTo() {
        return attribute.getDataFaker().getTo();
    }

    public String getGeneratorDataType() {
        return attribute.getDataFaker().getGeneratorDataType();
    }

    public String getSpecificType() {
        return attribute.getDataFaker().getSpecificType();
    }

}
