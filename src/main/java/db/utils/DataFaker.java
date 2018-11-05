/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.RandomService;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Maroine
 */
public abstract class DataFaker {

    protected String generatorType;
    protected Faker faker;
    protected RandomService random;
    protected boolean unique;
    protected int howMuch;
    protected String from;
    protected String to;

    protected String generatorDataType;
    protected String specificType;

    public DataFaker(String from, String to, int howMuch, boolean unique, String generatorDataType, String specificType) {
        this.faker = new Faker();
        this.random = faker.random();
        this.howMuch = howMuch;
        this.unique = unique;
        this.generatorDataType = generatorDataType;
        this.specificType = specificType;
    }

    public abstract String generateValue();

    private ArrayList<String> generateValues() {
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < howMuch; i++) {
            keys.add(generateValue());
        }
        return keys;
    }

    private ArrayList<String> generateUniqueValues() {
        HashSet<String> keys = new HashSet<>();
        for (int i = 0; i < howMuch; i++) {
            String value = null;
            do {
                value = generateValue();
            } while (keys.contains(value));
            keys.add(value);
        }
        return new ArrayList<>(keys);
    }

    public ArrayList<String> values() {
        if (!unique) {
            return generateValues();
        } else {
            return generateUniqueValues();
        }
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setGeneratorDataType(String generatorDataType) {
        this.generatorDataType = generatorDataType;
    }

}
