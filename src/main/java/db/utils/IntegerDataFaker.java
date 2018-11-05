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
public class IntegerDataFaker extends DataFaker {

    private int from;
    private int to;

    public IntegerDataFaker() {
        super();
        this.from = Integer.parseInt(super.from);
        this.to = Integer.parseInt(super.from);
    }

    @Override
    public String generateValue() {
        return faker.random().nextInt(from, to) + "";
    }

}
