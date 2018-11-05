/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamac
 */
public class DateDataFaker extends DataFaker {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    public DateDataFaker(String from, String to, int howMuch, boolean unique, String generatorDataType, String specificType) {
        super(from, to, howMuch, unique, generatorDataType, specificType);
        if (!from.contains("-")) {
            this.from = "01-01-2000";
            this.to = "01-01-2015";
        }
    }

    @Override
    public String generateValue() {
        Date date = null;
        try {

            date = faker.date().between(dateFormat.parse(from), dateFormat.parse(to));
        } catch (ParseException ex) {
            System.out.println("mochkla");
            return null;
        }
        return "'" + dateFormat.format(date) + "'";
    }

}
