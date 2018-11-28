/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author amirouche
 */
public class DateUtil {

    public boolean CompareDate(String from, String to) throws ParseException {
        boolean result;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date toDate = dateFormat.parse(to);
        Date fromDate = dateFormat.parse(from);

        if (fromDate.before(toDate) || toDate.equals(fromDate)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
