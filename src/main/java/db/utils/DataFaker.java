/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import static db.utils.Shared.faker;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author tamac
 */
public abstract class DataFaker implements Serializable {

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
        this.from = "1";
        this.to = "100";
        this.generatorType = "'system";
        this.specificType = "'system";
        this.nullsNumber = (nullsRate * howMuch / 100);
        this.regex = "[a-zA-Z]";
    }

    protected int between() {
        return faker.random().nextInt(Integer.parseInt(from), Integer.parseInt(to));
    }

    protected double betweenReal() {
        return Math.random() * Double.parseDouble(to) + Double.parseDouble(from);
    }

    public abstract String generateValue();

    private void generateUniqueValues() {
        int rest = howMuch - attribute.getInstances().size() + attribute.getPreInstances().size();
        for (int i = 0; i < rest; i++) {
            String value = null;
            do {
                value = generateValue();
            } while (attribute.getInstances().contains(value));
            attribute.getInstances().add(generateValue());
        }
    }

    private void generateValues() {
        int rest = howMuch - attribute.getInstances().size() + attribute.getPreInstances().size();
        for (int i = 0; i < rest; i++) {
            attribute.getInstances().add(generateValue());
        }
    }

    public int getRest() {
        return howMuch - attribute.getInstances().size() + attribute.getPreInstances().size();
    }

    public void values() {
        if (attribute.isUnique() || attribute.isPrimary()) {
            generateUniqueValues();
        } else {
            generateValues();
        }
    }

    public void setConfiguration(String from, String to, String generatorType, String specificType) {
        this.from = from;
        this.to = to;
        this.generatorType = generatorType;
        this.specificType = specificType;
    }

    public String getGeneratorType() {
        return generatorType;
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

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
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
        this.nullsNumber = ((nullsRate * howMuch) / 100);
    }
}
