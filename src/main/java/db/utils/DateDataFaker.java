/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Maroine
 */
public class DateDataFaker extends DataFaker {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

    public DateDataFaker() {
        super();
    }

    @Override
    public String generateValue() {
        Date date = null;
        try {

            date = faker.date().between(dateFormat.parse(from), dateFormat.parse(to));
        } catch (ParseException ex) {
            return null;
        }
        return "'" + dateFormat.format(date) + "'";
    }

}
