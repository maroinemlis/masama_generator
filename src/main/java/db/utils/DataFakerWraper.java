/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.util.ArrayList;

/**
 *
 * @author tamac
 */
public class DataFakerWraper {

    DataFaker dataFaker;

    public DataFakerWraper(String dataType, String from, String to, int howMuch, boolean unique, String generatorDataType, String specificType) {
        switch (dataType) {
            case "TEXT":
                dataFaker = new TextDataFaker(from, to, howMuch, unique, generatorDataType, specificType);
                break;
            case "DATE":
                dataFaker = new DateDataFaker(from, to, howMuch, unique, generatorDataType, specificType);
                break;
            case "INT":
            case "INTEGER":
            case "DOUBLE":
            case "FLOAT":
                dataFaker = new IntegerDataFaker(from, to, howMuch, unique, generatorDataType, specificType);
                break;
        }
    }

    public ArrayList<String> values() {
        return dataFaker.values();
    }

    public void setGeneratorType(String generatorType) {
        dataFaker.generatorType = generatorType;
    }

    public void setUnique(boolean unique) {
        dataFaker.setUnique(unique);
    }

    public void setHowMuch(int howMuch) {
        dataFaker.setHowMuch(howMuch);
    }

    public void setFrom(String from) {
        dataFaker.setFrom(from);

        dataFaker.from = from;
    }

    public void setTo(String to) {
        dataFaker.setTo(to);
    }

    public void setGeneratorDataType(String generatorDataType) {
        dataFaker.setGeneratorDataType(generatorDataType);

    }
}
