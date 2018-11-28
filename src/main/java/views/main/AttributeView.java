/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import db.bean.Attribute;
import db.utils.DateDataFaker;
import db.utils.IntegerDataFaker;
import db.utils.TextDataFaker;
import db.utils.DataFaker;

import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;

/**
 *
 * @author tamac
 */
public class AttributeView {

    Attribute attribute;
    private DataFaker dataFaker;
    private Control fromControl;
    private Control toControl;
    private JFXComboBox typesCombo;
    private JFXComboBox typesCombo2;

    public AttributeView(Attribute attribute) {
        this.attribute = attribute;
        switch (attribute.getDataType()) {
            case "TEXT":
                dataFaker = new TextDataFaker(attribute);
                fromControl = new JFXSlider();
                toControl = new JFXSlider();
                break;
            case "DATE":
                dataFaker = new DateDataFaker(attribute);
                fromControl = new DatePicker();
                toControl = new DatePicker();
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                fromControl = new JFXTextField();
                toControl = new JFXTextField();
                dataFaker = new IntegerDataFaker(attribute);
                break;
        }
    }

    public void startToGenerateRootValues(int howMuch) {
        dataFaker.setHowMuch(howMuch);
        attribute.startToGenerateRootValues();
    }

    public DataFaker getDataFaker() {
        return dataFaker;
    }
}
