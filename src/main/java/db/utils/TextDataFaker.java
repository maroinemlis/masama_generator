/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

/**
 *
 * @author Maroine
 */
public class TextDataFaker extends DataFaker {

    private int from;
    private int to;

    public TextDataFaker() {
        super();
        this.from = Integer.parseInt(super.from);
        this.to = Integer.parseInt(super.from);
    }

    @Override
    public String generateValue() {
        String str = null;
        int length = faker.random().nextInt(from, to);
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
