/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Address;
import com.github.javafaker.Artist;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamac
 */
public class TextDataFaker extends DataFaker {

    private int from;
    private int to;

    public TextDataFaker(String from, String to, int howMuch, boolean unique, String generatorDataType, String specificType) {
        super(from, to, howMuch, unique, generatorDataType, specificType);
        this.from = Integer.parseInt(from);
        this.to = Integer.parseInt(to);
    }

    @Override
    public String generateValue() {
        String str = null;
        int length = random.nextInt(from, to);
        if (generatorDataType == null) {
            str = faker.regexify("[a-zA-Z]{" + length + "}");

        } else {
            try {
                Object result = faker.getClass().getMethod(generatorDataType, null).invoke(faker);
                str = result.getClass().getMethod(specificType, null).invoke(result).toString();
            } catch (Exception ex) {
            }
        }
        return str;
    }
}
