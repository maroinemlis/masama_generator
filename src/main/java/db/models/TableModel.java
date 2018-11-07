/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.models;

import db.bean.Attribute;
import db.bean.ForeignKey;
import db.bean.Table;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author tamac
 */
public class TableModel {

    private Table table;
    private int howMuch;
    private int nullsRate;

    public TableModel() {
        this.howMuch = 10;
        this.nullsRate = 0;
    }

    /**
     * Generates instances exemples for each attribtute
     *
     * @throws ParseException
     */
    private void startToGenerateInstances() throws ParseException {
        for (Attribute a : table.getAttributes()) {
            a.startToGenerateRootValues(howMuch, nullsRate);
        }
        for (ForeignKey foreignKey : table.getForeignKeys()) {
            List<Attribute> fkTuple = foreignKey.getFkTuple();
            List<Attribute> pkTuple = foreignKey.getPkTuple();
            for (int i = 0; i < pkTuple.size(); i++) {
                fkTuple.get(i).setInstances(pkTuple.get(i).getInstances());
            }
        }
    }

    /**
     * Shows instances exemples for each attribtute
     */
    public void show() {
        List<Attribute> attributes = table.getAttributes();
        int rowNumber = attributes.get(0).getInstances().size();
        for (int j = 0; j < rowNumber - 1; j++) {
            String insert = "INSERT INTO " + table.getTableName() + " VALUES (";
            int i = 0;
            Attribute a = null;
            for (; i < attributes.size() - 1; i++) {
                a = attributes.get(i);
                insert += a.getInstances().get(j) + ", ";
            }
            a = attributes.get(i);
            insert += a.getInstances().get(j) + ");";
            System.out.println(insert);
        }

    }
}
