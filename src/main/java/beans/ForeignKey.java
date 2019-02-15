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
     * Initializes a newly created ForeignKey. If their are tuple of foreign key
     * then the first take foreignKeyNumber = 1 and the second foreignKeyNumber
     * = 2
     *
     * @param references the table referenced
     * @param foreignKeyNumber the number of foreign key
     */
    public ForeignKey(Table references, int foreignKeyNumber) {
        this.references = references;
        this.foreignKeyNumber = foreignKeyNumber;
    }

    /**
     * Add primary key and foreign key part of the two tuples
     *
     * @param fkTuplePart the foreign key
     * @param pkTuplePart the primary key
     */
    public void addToTupels(Attribute fkTuplePart, Attribute pkTuplePart) {
        this.fkTuple.add(fkTuplePart);
        this.pkTuple.add(pkTuplePart);
    }

    /**
     * Return the tuple (list of attribute) of Foreign key.
     *
     * @return list of foreign keys attribute
     */
    public List<Attribute> getFkTuple() {
        return fkTuple;
    }

    /**
     * Return the tuple (list of attribute) Primary key
     *
     * @return list of primary keys attribute
     */
    public List<Attribute> getPkTuple() {
        return pkTuple;
    }

    /**
     * Return the number(a unique id) of foreign key in the table
     *
     * @return the id of foreign key
     */
    public int getForeignKeyNumber() {
        return this.foreignKeyNumber;
    }

    /**
     * Return the table that the tuple of Foreign key is referring to it
     *
     * @return Table
     */
    public Table getReferences() {
        return references;
    }

}
