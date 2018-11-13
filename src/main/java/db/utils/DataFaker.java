/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import com.github.javafaker.Faker;
import db.bean.Attribute;
import java.util.LinkedList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Maroine
 */
public abstract class DataFaker {

    protected Faker faker;
    protected int howMuch;
    protected int nullsRate;
    protected String from;
    protected String to;
    protected String generatorType;
    protected String specificType;
    protected String regex;
    protected Attribute attribute;
    private int nullsNumber;

    public DataFaker(Attribute att) {
        this.attribute = att;
        this.faker = new Faker();
        this.from = "3";
        this.to = "100";
        this.generatorType = "system";
        this.specificType = "system";
        this.nullsNumber = (nullsRate * howMuch / 100);
        this.regex = "[a-zA-Z]";
    }

    protected int between() {
        return faker.random().nextInt(Integer.parseInt(from), Integer.parseInt(to));
    }

    public abstract String generateValue();

    private void generateNulls(Collection<String> values) {
        for (int i = 0; i < nullsNumber; i++) {
            values.add("NULL");
        }
    }

    private LinkedList<String> generateValues() {
        LinkedList<String> values = new LinkedList<>();
        generateNulls(values);
        for (int i = 0; i < howMuch - nullsNumber; i++) {
            values.add(generateValue());
        }
        return values;
    }

    private LinkedList<String> generateUniqueValues() {
        HashSet<String> values = new HashSet<>();
        generateNulls(values);
        for (int i = 0; i < howMuch - nullsNumber; i++) {
            String value = null;
            do {
                value = generateValue();
            } while (values.contains(value));
            values.add(value);
        }
        return new LinkedList<>(values);
    }

    public LinkedList<String> values() {
        if (attribute.isUnique() || attribute.isPrimary()) {
            return generateUniqueValues();
        } else {
            return generateValues();
        }
    }

    public void setConfiguration(String from, String to,
            String generatorType, String specificType) {
        this.from = from;
        this.to = to;
        this.generatorType = generatorType;
        this.specificType = specificType;
    }

    public String getGeneratorType() {
        return generatorType;
    }

    public Faker getFaker() {
        return faker;
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

    public String getSpecificType() {
        return specificType;
    }

    public int getNullsRate() {
        return nullsRate;
    }

    public void setHowMuch(int howMuch) {
        this.howMuch = howMuch;
    }

    public void setNullsRate(int nullsRate) {
        this.nullsRate = nullsRate;
    }

}
