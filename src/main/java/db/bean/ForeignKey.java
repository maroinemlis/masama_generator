/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import java.util.List;
import static db.utils.StringUtil.tupelToString;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * An object represente a foreign key constraint for a table. constraint foreign
 * key (fkTuple) references pkTuple(references)
 *
 * @author Maroine
 */
public class ForeignKey implements Serializable {

    private List<Attribute> fkTuple = new LinkedList<>();
    private Table references;
    private List<Attribute> pkTuple = new LinkedList<>();
    private int foreignKeyNumber;

    /**
     *
     * @param references
     * @param foreignKeyNumber
     */
    public ForeignKey(Table references, int foreignKeyNumber) {
        this.references = references;
        this.foreignKeyNumber = foreignKeyNumber;
    }

    /**
     * Add primary key and foreign key part of the two tupels
     *
     * @param fkTuplePart
     * @param pkTuplePart
     */
    public void addToTupels(Attribute fkTuplePart, Attribute pkTuplePart) {
        this.fkTuple.add(fkTuplePart);
        this.pkTuple.add(pkTuplePart);
    }

    public List<Attribute> getFkTuple() {
        return fkTuple;
    }

    public List<Attribute> getPkTuple() {
        return pkTuple;
    }

    /**
     *
     * @return a forein key a unque id in the table
     */
    public int getForeignKeyNumber() {
        return this.foreignKeyNumber;
    }

    public Table getReferences() {
        return references;
    }

    @Override
    public String toString() {
        return "FOREIGN KEY" + tupelToString(fkTuple) + " REFERENCES " + references.getTableName() + "" + tupelToString(pkTuple);
    }
}
