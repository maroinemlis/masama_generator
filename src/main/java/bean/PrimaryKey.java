/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An object represente a primary key constraint for a table
 *
 * @author Maroine
 */
public class PrimaryKey implements Serializable {

    private List<Attribute> tuple = new ArrayList<>();

    /**
     * Get the tuple Primary key
     *
     * a tuple is a list of attribute
     *
     * @return List<Attribute>
     */
    public List<Attribute> getTuple() {
        return tuple;
    }

    /**
     * Add an atribute to the list(tuple) of primary key
     *
     * @param Attribute
     */
    public void addToPrimaryKey(Attribute a) {
        tuple.add(a);
    }

    /**
     * Return the description of primary key list in a string
     *
     * @return String
     */
}
