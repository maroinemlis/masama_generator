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
    public String generateValue() {
        return between() + "";
    }

}
