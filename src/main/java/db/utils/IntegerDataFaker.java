/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;

/**
 *
 * @author Maroine
 */
public class IntegerDataFaker extends DataFaker {

    public IntegerDataFaker(Attribute att) {
        super(att);
    }

    @Override
    protected void generateUniqueValues() {
        int rest = getRest();
        int from = Integer.parseInt(this.from);
        int to = Integer.parseInt(this.to);
        int j = from;
        int gap = (to - from) / howMuch;
        for (int i = 0; i < rest; i++) {
            String value = "" + j;
            attribute.getInstances().add(value);
            j += between(1, gap);
        }
    }

    @Override
    public String generateValue() {
        return between() + "";
    }

}
