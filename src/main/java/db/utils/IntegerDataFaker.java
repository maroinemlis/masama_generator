/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import java.util.List;

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

    @Override
    public List<String> generateValue(List<String> preDate, int size) {
        return DataGenerator.generateUniqueInt(preDate, Integer.valueOf(from), Integer.valueOf(to), size);
    }

}
