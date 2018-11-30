/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import db.utils.DataFaker;
import db.utils.DateDataFaker;
import db.utils.IntegerDataFaker;
import db.utils.TextDataFaker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A object represent a column of SQL table
 *
 * @author Maroine
 */
public class Attribute implements Serializable {

    private String name;
    private String dataType;
    private boolean isPrimary;
    private boolean isUnique;
    private boolean isNullable;
    private List<String> instances = new ArrayList<>();
    private DataFaker dataFaker;
    private Attribute reference;
    private List<Attribute> referencesMe = new ArrayList<Attribute>();

    /**
     * getReferencesMe represents the list attributes of the table
     *
     * @return List<Attribute>
     */
    public List<Attribute> getReferencesMe() {
        return referencesMe;
    }

    /**
     * Constructor for class Attribute
     *
     * @param attributeName The name of the attribute
     * @param dataType The type of the attribute
     * @param nullable Specify if the attribute is nullable
     */
    public Attribute(String attributeName, String dataType, String nullable) {
        this.name = attributeName;
        this.dataType = dataType;
        this.isPrimary = false;
        this.isUnique = false;
        this.isNullable = !nullable.equals("0");
        this.reference = null;
        switch (dataType) {
            case "TEXT":
                dataFaker = new TextDataFaker(this);
                break;
            case "DATE":
                dataFaker = new DateDataFaker(this);
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                dataFaker = new IntegerDataFaker(this);
                break;
        }

    }

    /**
     * Get the name of the attribute
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Get SQL data type
     *
     * @return String
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Set the name of the attribute
     *
     * @param attributeName The name of attribute
     */
    public void setName(String attributeName) {
        this.name = attributeName;
    }

    /**
     * Set the data type of the attribute
     *
     * @param dataType The type of attribute
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public boolean equals(Object o) {
        return name.equals(((Attribute) o).name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     *
     */
    public void startToGenerateRootValues() {
        instances.clear();
        dataFaker.values();
    }

    /**
     * Get a primary key, verify if the attribute is a primary key,
     *
     * @return boolean True if a primary key attribute, false if not
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * Set a primary key
     *
     * @param bool True if the attribute is a primary key
     */
    public void isPrimary(boolean bool) {
        isPrimary = bool;
    }

    public List<String> getInstances() {
        return this.instances;
    }

    public DataFaker getDataFaker() {
        return dataFaker;
    }

    public void setInstances(List<String> instances) {
        this.instances = instances;
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    /**
     * Verify if the attribute is unique
     *
     * @return boolean True if a unique atribute, false if not
     */
    public boolean isUnique() {
        return this.isUnique;
    }

    public Attribute getReference() {
        return reference;
    }

    public void setReferences(Attribute a) {
        this.reference = a;
    }

}
