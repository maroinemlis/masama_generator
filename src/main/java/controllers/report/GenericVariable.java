/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.report;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Maroine
 */
public class GenericVariable extends RecursiveTreeObject<GenericVariable> {

    private JFXTextField name;
    private JFXComboBox tableReference;
    private JFXComboBox attributeReference;
    private JFXSlider existanceRate;
    private JFXCheckBox update;
    private static ObservableList<GenericVariable> variblesList = FXCollections.<GenericVariable>observableArrayList();
    private List<String> instances;

    public List<String> getInstances() {
        return instances;
    }

    public static ObservableList<GenericVariable> getVariblesList() {
        return variblesList;
    }

    public GenericVariable(JFXTextField name, JFXComboBox tableReference, JFXComboBox attributeReference, JFXSlider existanceRate) {
        this.name = new JFXTextField(name.getText());
        this.tableReference = new JFXComboBox();
        this.tableReference.getItems().addAll(tableReference.getItems());
        this.tableReference.getSelectionModel().select(tableReference.getSelectionModel().getSelectedIndex());

        this.attributeReference = new JFXComboBox();
        this.attributeReference.getItems().addAll(attributeReference.getItems());
        this.attributeReference.getSelectionModel().select(attributeReference.getSelectionModel().getSelectedIndex());

        this.existanceRate = new JFXSlider();
        this.existanceRate.setValue(existanceRate.getValue());
        this.update = new JFXCheckBox();
    }

    public JFXTextField getName() {
        return name;
    }

    public JFXComboBox getTableReference() {
        return tableReference;
    }

    public JFXComboBox getAttributeReference() {
        return attributeReference;
    }

    public JFXSlider getExistanceRate() {
        return existanceRate;
    }

    public double getExistanceRate(boolean b) {
        return existanceRate.getValue();
    }

    public JFXCheckBox getUpdate() {
        return update;
    }
}
