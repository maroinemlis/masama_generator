/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import static db.utils.Shared.faker;
import java.util.Random;

/**
 *
 * @author Maroine
 */
public class DoubleDataFaker extends DataFaker {

    int from;
    int to;
    Random r;

    public DoubleDataFaker(Attribute att) {
        super(att);
        from = Integer.valueOf(super.from);
        to = Integer.valueOf(super.to);
        r = new Random();
    }

    @Override
    public String generateValue() {
        double randomValue = from + (to - from) * r.nextDouble();
        return randomValue + "";
    }

}
