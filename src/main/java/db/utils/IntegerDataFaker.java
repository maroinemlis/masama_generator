/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Faker;
import db.bean.Attribute;
import java.io.Serializable;

/**
 *
 * @author Maroine
 */
public class IntegerDataFaker extends DataFaker implements Serializable {

    public IntegerDataFaker(Attribute att) {
        super(att);
    }

    @Override
    public String generateValue(Faker faker) {
        return between(faker) + "";
    }

}
