package faker;

import beans.Attribute;
import static utils.Shared.faker;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Maroine
 */
public abstract class DataFaker implements Serializable {

    protected String from;
    protected String to;
    protected String generatorType;
    protected String specificType;
    protected String regex;
    protected Attribute attribute;
    protected int nullsNumber = 0;

    public DataFaker(Attribute attribute) {
        this.from = "1";
        this.to = "1000000";
        this.generatorType = "'system";
        this.specificType = "'system";
        this.regex = "[a-zA-Z]";
        this.attribute = attribute;
    }

    protected int between() {
        return between(Integer.parseInt(from), Integer.parseInt(to));
    }

    protected int between(int from, int to) {
        return faker.random().nextInt(from, to);
    }

    protected double betweenReal() {
        return betweenReal(Double.parseDouble(from), Double.parseDouble(to));

    }

    protected double betweenReal(double from, double to) {
        return from + Math.random() * to;
    }

    protected abstract String generateNewValue();

    protected void generateUniqueValues() {
        int howMuchItRest = howMuchItRest();
        List<String> instances = attribute.getInstances();
        for (int i = 0; i < howMuchItRest; i++) {
            String newValue;
            do {
                newValue = generateNewValue();
            } while (instances.contains(newValue));
            attribute.getInstances().add(newValue);
        }
    }

    protected void generateSimpleValues() {
        for (int i = 0; i < nullsNumber; i++) {
            attribute.getInstances().add("NULL");
        }
        int howMuchItRest = howMuchItRest();
        for (int i = 0; i < howMuchItRest; i++) {
            attribute.getInstances().add(generateNewValue());
        }
    }

    public void values() {
        if (attribute.isUnique() || attribute.isPrimary()) {
            generateUniqueValues();
        } else {
            generateSimpleValues();
        }
    }

    public int howMuchItRest() {
        return attribute.getTable().getHowMuch() - attribute.getInstances().size();
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void setConfiguration(String from, String to, String generatorType, String specificType) {
        this.from = from;
        this.to = to;
        this.generatorType = generatorType;
        this.specificType = specificType;
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

    public void setFromToNullsRate(String from, String to, int nullsRate) {
        this.from = from;
        this.to = to;
    }

}
