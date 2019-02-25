/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import beans.Attribute;
import static utils.Shared.faker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Generate values for Date type
 *
 * @author Maroine
 */
public class DateDataFaker extends DataFaker {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    /**
     * Initialize the default minimum and maximum value of date.
     *
     * @param att
     */
    public DateDataFaker(Attribute att) {
        super(att);
        this.from = "2015-01-01";
        this.to = "2018-05-01";
    }

    /**
     * Generate date value between min(from) and max(to).
     *
     * @return
     */
    @Override
    public String generateNewValue() {
        Date date = null;
        try {
            date = faker.date().between(dateFormat.parse(from), dateFormat.parse(to));
        } catch (ParseException ex) {
            return null;
        }
        return "'" + dateFormat.format(date) + "'";
    }
}
