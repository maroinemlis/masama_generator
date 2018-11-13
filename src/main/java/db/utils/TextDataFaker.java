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
public class TextDataFaker extends DataFaker {

    public TextDataFaker(Attribute att) {
        super(att);
    }

    @Override
    public String generateValue() {
        String str = null;
        int length = between();
        if (generatorType.equals("system")) {
            str = faker.regexify(regex + "{" + length + "}");
        } else {
            try {
                Object result = faker.getClass().getMethod(generatorType, null).invoke(faker);
                str = result.getClass().getMethod(specificType, null).invoke(result).toString();
            } catch (Exception ex) {
            }
        }
        return str;
    }
}
