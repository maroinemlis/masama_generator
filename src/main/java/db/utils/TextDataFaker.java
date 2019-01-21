/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import static db.utils.Shared.faker;
import java.util.ArrayList;
import java.util.List;

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
        if (generatorType.equals("'system")) {
            str = faker.regexify(regex + "{" + length + "}");
        } else {
            try {
                Object result = faker.getClass().getMethod(generatorType, null).invoke(faker);
                str = result.getClass().getMethod(specificType, null).invoke(result).toString();
            } catch (Exception ex) {
            }
        }
        return "'" + str + "'";
    }

    @Override
    public List<String> generateValue(List<String> collectPreDate, int size) {
        List<String> result = new ArrayList<>();
        result.addAll(collectPreDate);
        while (result.size() < size) {
            String d = generateValue();
            if (!result.contains(d)) {
                result.add(d);
            }
        }
        return result;
    }
}
