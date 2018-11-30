/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.List;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Maroine
 */
public class InstancesModel extends RecursiveTreeObject<InstancesModel> {

    public List<StringProperty> instances;

    /**
     * Constructor for class InstancesModel
     *
     * @param List<StringProperty>
     */
    public InstancesModel(List<StringProperty> instances) {
        this.instances = instances;
    }

    /**
     * get a list of instances
     *
     * @return List<StringProperty>
     */
    public List<StringProperty> getInstances() {
        return instances;
    }
}
