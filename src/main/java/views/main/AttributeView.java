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

import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;

/**
 *
 * @author tamac
 */
public class AttributeView {

    Attribute attribute;
    private Control fromControl;
    private Control toControl;
    private JFXComboBox typesCombo;
    private JFXComboBox typesCombo2;

    public AttributeView(Attribute attribute) {
        this.attribute = attribute;
        switch (attribute.getDataType()) {

            case "TEXT":
                fromControl = new JFXSlider();
                toControl = new JFXSlider();
                break;
            case "DATE":
                fromControl = new DatePicker();
                toControl = new DatePicker();
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
            case "REAL":
                fromControl = new JFXTextField();
                toControl = new JFXTextField();
                ((JFXTextField) (fromControl)).textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d([\\.]\\d)?")) {
                        ((JFXTextField) (fromControl)).setText(oldValue);
                    }
                });
                ((JFXTextField) (toControl)).textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d([\\.]\\d)?")) {
                        ((JFXTextField) (fromControl)).setText(oldValue);
                    }
                });
                break;
        }
    }

    public void startToGenerateRootValues(int howMuch) {
        attribute.getDataFaker().setHowMuch(howMuch);
        attribute.startToGenerateRootValues();
    }

}
