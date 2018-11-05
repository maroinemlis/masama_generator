/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import java.util.ArrayList;
import java.util.List;
import static db.utils.StringUtil.tupelToString;

/**
 * An object represente a foreign key constraint for a table. constraint foreign
 * key (fkTuple) references pkTuple(references)
 *
 * @author Maroine
 */
public class ForeignKey extends Constraint {

    private List<Attribute> fkTuple = new ArrayList();
    private Table references;
    private List<Attribute> pkTuple = new ArrayList();
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
     * @param pkPart
     * @param fkPart
     */
    public void addToTupels(Attribute fkPart, Attribute pkPart) {
        fkTuple.add(fkPart);
        pkTuple.add(pkPart);

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

    @Override
    public String toString() {
        return "FOREIGN KEY" + tupelToString(fkTuple) + " REFERENCES " + references.getTableName() + "" + tupelToString(pkTuple);
    }
}
