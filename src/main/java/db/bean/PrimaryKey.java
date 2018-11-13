/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.bean;

import db.utils.StringUtil;
import java.util.LinkedList;
import java.util.List;

/**
 * An object represente a primary key constraint for a table
 *
 * @author Maroine
 */
public class PrimaryKey extends Constraint {

    private List<Attribute> tuple = new LinkedList();

    public List<Attribute> getTuple() {
        return tuple;
    }

    public void addToPrimaryKey(Attribute a) {
        tuple.add(a);
    }

    public String toString() {
        return "PRIMARY KEY" + StringUtil.tupelToString(tuple);
    }
}
