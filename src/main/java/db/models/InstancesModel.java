/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.bean.Attribute;
import java.util.List;

/**
 *
 * @author Maroine
 */
public class InstancesModel extends RecursiveTreeObject<InstancesModel> {

    private List<Attribute> attributes;
    private int row;

    public InstancesModel(List<Attribute> attributes, int row) {
        this.attributes = attributes;
        this.row = row;
    }

    public String getColumn0() {
        return attributes.get(0).getInstances().get(row);
    }

    public String getColumn1() {
        return attributes.get(1).getInstances().get(row);
    }

    public String getColumn2() {
        return attributes.get(2).getInstances().get(row);

    }

    public String getColumn3() {
        return attributes.get(3).getInstances().get(row);

    }

    public String getColumn4() {
        return attributes.get(4).getInstances().get(row);
    }

    public String getColumn5() {
        return attributes.get(5).getInstances().get(row);
    }

    public String getColumn6() {
        return attributes.get(6).getInstances().get(row);
    }

    public String getColumn7() {
        return attributes.get(7).getInstances().get(row);
    }

    public String getColumn8() {
        return attributes.get(8).getInstances().get(row);
    }

    public String getColumn9() {
        return attributes.get(9).getInstances().get(row);
    }

    public String getColumn10() {
        return attributes.get(10).getInstances().get(row);
    }

    public String getColumn11() {
        return attributes.get(11).getInstances().get(row);
    }

    public String getColumn12() {
        return attributes.get(12).getInstances().get(row);
    }
}
