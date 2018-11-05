/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

/**
 *
 * @author tamac
 */
public class IntegerDataFaker extends DataFaker {

    private int from;
    private int to;

    public IntegerDataFaker(String from, String to, int howMuch, boolean unique, String generatorDataType, String specificType) {
        super(from, to, howMuch, unique, generatorDataType, specificType);
        this.from = Integer.parseInt(from);
        this.to = Integer.parseInt(to);

    }

    @Override
    public String generateValue() {
        return faker.random().nextInt(from, to) + "";
    }

}
