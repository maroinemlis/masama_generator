/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import beans.Attribute;
import static utils.Shared.faker;

/**
 * Generate values for Text type.
 *
 * @author Maroine
 */
public class TextDataFaker extends DataFaker {

    /**
     * Initialize the default value of minimum and maximum size of text.
     *
     * @param att
     */
    public TextDataFaker(Attribute att) {
        super(att);
        this.from = "1";
        this.to = "200";
    }

    /**
     * Generate text value withe the specified size and type of value
     *
     * @return
     */
    @Override
    public String generateNewValue() {
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

}
