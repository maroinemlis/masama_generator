/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Faker;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
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
    private boolean nullable;

    public DataFaker() {
        this.faker = new Faker();
        this.unique = false;
        this.howMuch = 10;
        this.from = "3";
        this.to = "10";
        this.generatorDataType = "System";
        this.specificType = "System";
        this.nullsRate = 10;
        this.nullable = true;
    }

    public abstract String generateValue();

    private void generateNulls(Collection<String> values) {
        int nulls = (nullsRate * howMuch / 100);
        for (int i = 0; i < nulls; i++) {
            values.add("NULL");
        }
    }

    private ArrayList<String> generateValues() {
        ArrayList<String> values = new ArrayList<>();
        generateNulls(values);
        for (int i = 0; i < howMuch - (nullsRate * howMuch / 100); i++) {
            values.add(generateValue());
        }
        return values;
    }

    private ArrayList<String> generateUniqueValues() {
        HashSet<String> values = new HashSet<>();
        generateNulls(values);
        for (int i = 0; i < howMuch; i++) {
            String value = null;
            do {
                value = generateValue();
            } while (values.contains(value));
            values.add(value);
        }
        return new ArrayList<>(values);
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

    public String getGeneratorType() {
        return generatorType;
    }

    public Faker getFaker() {
        return faker;
    }

    public boolean isUnique() {
        return unique;
    }

    public int getHowMuch() {
        return howMuch;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getGeneratorDataType() {
        return generatorDataType;
    }

    public String getSpecificType() {
        return specificType;
    }

    public int getNullsRate() {
        return nullsRate;
    }

    public void setHowMuch(int rows) {
        this.howMuch = rows;
    }

    public void setNullsRate(int nullsRate) {
        this.nullsRate = nullsRate;
    }

    public boolean isNullable() {
        return this.nullable;
    }

}
