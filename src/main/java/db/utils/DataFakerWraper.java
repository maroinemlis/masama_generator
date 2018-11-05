/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class DataFakerWraper {

    DataFaker dataFaker;

    public DataFakerWraper(String dataType) {
        switch (dataType) {
            case "TEXT":
                dataFaker = new TextDataFaker();
                break;
            case "DATE":
                dataFaker = new DateDataFaker();
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                dataFaker = new IntegerDataFaker();
                break;
        }
    }

    public ArrayList<String> values() {
        return dataFaker.values();
    }

    public void setConfiguration(String from, String to, int howMuch,
            String generatorDataType, String specificType, int nullsRate) {
        dataFaker.setConfiguration(from, to, howMuch, generatorDataType, specificType, nullsRate);
    }
}
