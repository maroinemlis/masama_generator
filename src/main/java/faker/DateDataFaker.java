/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import bean.Attribute;
import static utils.Shared.faker;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Maroine
 */
public class DateDataFaker extends DataFaker {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

    public DateDataFaker(Attribute att) {
        super(att);
        this.from = "2015-01-01";
        this.to = "2018-05-01";
    }

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
