/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faker;

import beans.Attribute;
import beans.SQLSchema;

/**
 * Generate values for Double type.
 *
 * @author Maroine
 */
public class RealDataFaker extends DataFaker {

    public RealDataFaker(Attribute att) {
        super(att);
    }

    /**
     * Generate Unique Double value
     */
    @Override
    protected void generateUniqueValues() {
        //super.generateUniqueValues();
        double from = Double.parseDouble(this.from);
        double to = Double.parseDouble(this.to);
        double gap = (to - from) / attribute.getTable().getHowMuch();
        double j = from;
        double a = 0;
        for (int i = 0; i < attribute.getTable().getHowMuch(); i++) {
            do {
                a = betweenReal(0, gap);
            } while (a == 0);
            j += a;
            attribute.getInstances().add(j + "");
        }
    }

    /**
     * Generate Double value
     *
     * @return
     */
    @Override
    protected String generateNewValue() {
        return betweenReal() + "";
    }

}
