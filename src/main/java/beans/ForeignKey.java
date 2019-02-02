/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An object represent a foreign key constraint for a table. constraint foreign
 * key (fkTuple) references pkTuple(references)
 *
 * @author Maroine
 */
public class ForeignKey implements Serializable {

    private List<Attribute> fkTuple = new ArrayList<>();
    private Table references;
    private List<Attribute> pkTuple = new ArrayList<>();
    private int foreignKeyNumber;

    /**
     * Construcor for class ForeignKey
     *
     * @param references its type is Table
     * @param foreignKeyNumber its type is int
     */
    public ForeignKey(Table references, int foreignKeyNumber) {
        this.references = references;
        this.foreignKeyNumber = foreignKeyNumber;
    }

    /**
     * Add primary key and foreign key part of the two tupels
     *
     * @param fkTuplePart an Attribute
     * @param pkTuplePart an Attribute
     */
    public void addToTupels(Attribute fkTuplePart, Attribute pkTuplePart) {
        this.fkTuple.add(fkTuplePart);
        this.pkTuple.add(pkTuplePart);
    }

    /**
     * Get the tuple of Foreign key
     *
     * a tuple is a list of attribute
     *
     * @return List<Attribute>
     */
    public List<Attribute> getFkTuple() {
        return fkTuple;
    }

    /**
     * Get the tuple Primary key
     *
     * a tuple is a list of attribute
     *
     * @return List<Attribute>
     */
    public List<Attribute> getPkTuple() {
        return pkTuple;
    }

    /**
     * Get the number(a unique id) of foreign key in the table
     *
     * @return int
     */
    public int getForeignKeyNumber() {
        return this.foreignKeyNumber;
    }

    /**
     * get the table that the tuple Foreign key is referring to it
     *
     * @return Table
     */
    public Table getReferences() {
        return references;
    }

}
