/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Faker;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Maroine
 */
public abstract class DataFaker {

    protected String generatorType;
    protected Faker faker;
    protected boolean unique;
    protected int howMuch;
    protected String from;
    protected String to;
    protected String generatorDataType;
    protected String specificType;
    protected int nullsRate;

    public DataFaker() {
        this.faker = new Faker();
        this.unique = false;
        this.howMuch = 10;
        this.from = "3";
        this.to = "10";
        this.generatorDataType = null;
        this.specificType = null;
        this.nullsRate = 10;
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

    public void setConfiguration(String from, String to, int howMuch,
            String generatorDataType, String specificType, int nullsRate) {
        this.from = from;
        this.to = to;
        this.howMuch = howMuch;
        this.generatorDataType = generatorDataType;
        this.specificType = specificType;
        this.nullsRate = nullsRate;

    }

}
