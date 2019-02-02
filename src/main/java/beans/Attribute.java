package beans;

import faker.BlobDataFaker;
import faker.BooleanDataFaker;
import faker.DataFaker;
import faker.DateDataFaker;
import faker.IntegerDataFaker;
import faker.RealDataFaker;
import faker.TextDataFaker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maroine
 */
public class Attribute implements Serializable {

    private String name;
    private String dataType;
    private boolean isPrimary;
    private boolean isUnique;
    private boolean isNullable;
    private List<String> instances = new ArrayList<>();

    private DataFaker dataFaker;
    private List<Attribute> referencesMe = new ArrayList<>();
    private List<Attribute> references = new ArrayList<>();
    private Table table;
    private boolean isCircular = false;
    private boolean isGenerated = false;

    @Override
    public boolean equals(Object o) {
        return ((Attribute) o).name.equals(name);
    }

    public boolean isGenerated() {
        return isGenerated;
    }

    public void isGenerated(boolean isGenerated) {
        this.isGenerated = isGenerated;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public Table getTable() {
        return table;
    }

    public void testIfCircular(List<Attribute> accumulator, Attribute _this) {

        for (Attribute a : references) {
            if (accumulator.contains(a)) {
                return;
            }
            if (a == _this) {
                _this.isCircular = true;
                return;
            } else {
                accumulator.add(a);
                a.testIfCircular(accumulator, _this);
            }
        }
    }

    public List<Attribute> getReferences() {
        return references;
    }

    public List<Attribute> getReferencesMe() {
        return referencesMe;
    }

    public Attribute(Table table, String attributeName, String dataType, String nullable) {
        this.table = table;
        this.name = attributeName;
        this.dataType = dataType;
        this.isPrimary = false;
        this.isUnique = false;
        this.isNullable = !nullable.equals("0");
        switch (dataType) {
            case "TEXT":
                dataFaker = new TextDataFaker(this);
                break;
            case "DATE":
                dataFaker = new DateDataFaker(this);
                break;
            case "INT":
            case "NUMERIC":
            case "INTEGER":
                dataFaker = new IntegerDataFaker(this);
                break;
            case "DOUBLE":
            case "FLOAT":
            case "REAL":
                dataFaker = new RealDataFaker(this);
                break;
            case "BOOLEAN":
                dataFaker = new BooleanDataFaker(this);
                break;
            case "BLOB":
                dataFaker = new BlobDataFaker(this);
                break;
        }

    }

    /**
     * get the name of the attribute
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get SQL data type
     *
     * @return String
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Set the name of the attribute
     *
     * @param attributeName The name of the attribute
     */
    public void setName(String attributeName) {
        this.name = attributeName;
    }

    /**
     * Set the data type of the attribute
     *
     * @param dataType The type of attribute
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void startToGenerateRootValues() {
        dataFaker.values();
        isGenerated = true;
    }

    public void startToGenerateWhoReference() {
        for (Attribute a : references) {
            if (!a.isGenerated) {
                a.instances.addAll(instances);
                a.isGenerated = true;
                a.startToGenerateWhoReference();
                a.startToGenerateWhoReferenceMe();

            }
        }
    }

    public void startToGenerateWhoReferenceMe() {
        for (Attribute a : referencesMe) {
            if (!a.isGenerated) {
                a.instances.addAll(instances);
                a.isGenerated = true;
                a.startToGenerateWhoReferenceMe();
                a.startToGenerateWhoReference();
            }
        }
    }

    public void fixInstances() {
        if (table.getHowMuch() == instances.size()) {
            return;
        }
        if (isPrimary || isUnique) {
            instances = instances.subList(0, table.getHowMuch());
        } else {
            List<String> newInstances = new ArrayList<>();
            int rest = table.getHowMuch();
            if (rest > 0) {
                int restDiv = rest / instances.size();
                for (int i = 0; i < restDiv; i++) {
                    newInstances.addAll(instances);
                    rest = rest - instances.size();
                }
                newInstances.addAll(instances.subList(0, rest));
                instances = newInstances;
            }

        }
    }

    /**
     * verify if the attribute is a primary key,
     *
     * @return boolean True if the attribute is a primary key, false if not
     */
    public boolean isPrimary() {
        return isPrimary;
    }

    /**
     * define a primary key
     *
     * @param bool True if the attribute is a primary key
     */
    public void isPrimary(boolean bool) {
        isPrimary = bool;
    }

    /**
     * get the list of instances
     *
     * @return List<String>
     */
    public List<String> getInstances() {
        return this.instances;
    }

    /**
     * get a dataFaker generation
     *
     * @param DataFaker
     */
    public DataFaker getDataFaker() {
        return dataFaker;
    }

    /**
     * Set the instance
     *
     * @param List<String>
     */
    public void setInstances(List<String> instances) {
        this.instances = instances;
    }

    public boolean isNullable() {
        return this.isNullable;
    }

    /**
     * Verify if the attribute is unique,True if a unique atribute, false if not
     *
     * @return boolean
     */
    public boolean isUnique() {
        return this.isUnique;
    }

    @Override
    public String toString() {
        return table.getTableName() + "(" + this.name + ")";
    }

}
