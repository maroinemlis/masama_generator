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
public class RealDataFaker extends DataFaker {

    public RealDataFaker(Attribute att) {
        super(att);
    }

    @Override
    public String generateValue() {
        return betweenReal() + "";
    }
}
