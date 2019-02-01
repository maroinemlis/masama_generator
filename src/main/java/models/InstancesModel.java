package models;

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
