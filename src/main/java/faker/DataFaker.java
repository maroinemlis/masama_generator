package faker;

import beans.Attribute;
import static utils.Shared.faker;
import java.io.Serializable;
import java.util.List;

/**
 * An abstract class define a generic sql data type.
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

    /**
     * Initialize the default settings for the attribute
     *
     * @param attribute
     */
    public DataFaker(Attribute attribute) {
        this.from = "1";
        this.to = "1000000";
        this.generatorType = "'system";
        this.specificType = "'system";
        this.regex = "[a-zA-Z]";
        this.attribute = attribute;
    }

    /**
     * Return Integer value in the interval [from , to] using th method
     * between(from, to).
     *
     * @return
     */
    protected int between() {
        return between(Integer.parseInt(from), Integer.parseInt(to));
    }

    /**
     * Return Integer value in the interval [from , to].
     *
     * @param from minimum value
     * @param to maximum value
     * @return integer value between from and to
     */
    protected int between(int from, int to) {
        return faker.random().nextInt(from, to);
    }

    /**
     * Return Real(Double) value in the interval [from , to] using th method
     * betweenReal(from, to).
     *
     * @return double value between from and to
     */
    protected double betweenReal() {
        return betweenReal(Double.parseDouble(from), Double.parseDouble(to));

    }

    /**
     * Return Real(Double) value in the interval [from , to].
     *
     * @return double value between from and to
     */
    protected double betweenReal(double from, double to) {
        return from + Math.random() * to;
    }

    /**
     * The most important method that will be defined for each concrete sql data
     * type
     *
     * @return value generated
     */
    protected abstract String generateNewValue();

    /**
     * Fill the instance list with distinct values for an attribute
     */
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

    /**
     * Fill the instance list with a normal values for an attribute
     *
     */
    protected void generateSimpleValues() {
        for (int i = 0; i < nullsNumber; i++) {
            attribute.getInstances().add("NULL");
        }
        int howMuchItRest = howMuchItRest();
        for (int i = 0; i < howMuchItRest; i++) {
            attribute.getInstances().add(generateNewValue());
        }
    }

    /**
     * Fill the instance list with the appropriate values
     */
    public void values() {
        if (attribute.isUnique() || attribute.isPrimary()) {
            generateUniqueValues();
        } else {
            generateSimpleValues();
        }
    }

    /**
     * Return How many instance (value to generate) it rest to complete the
     * generation
     *
     * @return how many instance it rest to complete the generation
     */
    public int howMuchItRest() {
        return attribute.getTable().getHowMuch() - attribute.getInstances().size();
    }

    /**
     * Set the Regex to use in generation
     *
     * @param regex
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * Set the setting for the attribute
     *
     * @param from minimum value
     * @param to maximum value
     * @param generatorType the type of data to generate
     * @param specificType
     */
    public void setConfiguration(String from, String to, String generatorType, String specificType) {
        this.from = from;
        this.to = to;
        this.generatorType = generatorType;
        this.specificType = specificType;
    }

    /**
     * Return the minimum value of intervale
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     * Return the maximum value of intervale
     *
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the minimum value of intervale
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Set the maximum value of intervale
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @param from
     * @param to
     * @param nullsRate
     */
    public void setFromToNullsRate(String from, String to, int nullsRate) {
        this.from = from;
        this.to = to;
    }

}
