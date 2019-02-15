/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An object represent a primary key constraint for a table
 *
 * @author Maroine
 */
public class PrimaryKey implements Serializable {

    private List<Attribute> tuple = new ArrayList<>();

    /**
     * Return the tuple Primary key. a tuple is a list of attribute
     *
     * @return list represent primary keys of table
     */
    public List<Attribute> getTuple() {
        return tuple;
    }

    /**
     * Add an attribute to the list(tuple) of primary keys. In case of table
     * with one primary key then the list contain only one attribute
     *
     * @param attribute a primary key of table
     */
    public void addToPrimaryKey(Attribute attribute) {
        tuple.add(attribute);
    }
}
