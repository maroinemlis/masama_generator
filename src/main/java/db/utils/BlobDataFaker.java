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
public class BlobDataFaker extends DataFaker {

    Random r;

    public BlobDataFaker(Attribute att) {
        super(att);
        r = new Random();
    }

    @Override
    public String generateValue() {
        return faker.regexify("[a-f0-9]{" + between() + "}");
    }

}
