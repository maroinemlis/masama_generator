package db.bean;

import db.utils.BlobDataFaker;
import db.utils.BooleanDataFaker;
import db.utils.DataFaker;
import db.utils.DateDataFaker;
import db.utils.DoubleDataFaker;
import db.utils.IntegerDataFaker;
import db.utils.TextDataFaker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An object represent a column of SQL table
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

    public List<Attribute> getReferences() {
        return references;
    }

    /**
     * get the list of attributes that make references to the current attribute
     * of the table
     *
     * @return List<Attribute>
     */
    public List<Attribute> getReferencesMe() {
        return referencesMe;
    }

    /**
     * Constructor for class Attribute
     *
     * @param attributeName The name of the attribute
     * @param dataType The type of the attribute
     * @param nullable Specify if the attribute is nullable
     */
    public Attribute(String attributeName, String dataType, String nullable) {
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
                dataFaker = new DoubleDataFaker(this);
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

    @Override
    public boolean equals(Object o) {
        return name.equals(((Attribute) o).name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * generate the attributes that do not have references
     *
     */
    public void startToGenerateRootValues() {
        dataFaker.values();
        startToGenerateWhoReferenceMe();

    }

    public void startToGenerateWhoReferenceMe() {
        referencesMe.forEach(ref -> {
            int diffrence = ref.dataFaker.getHowMuch() - ref.instances.size();
            if (diffrence > 0) {
                ref.instances.addAll(instances.stream().limit(diffrence).collect(Collectors.toList()));
            }
        });
        referencesMe.forEach(ref -> {
            ref.startToGenerateWhoReferenceMe();
        });

    }

    void fixInstancesHowMuch() {
        if (references.isEmpty()) {
            return;
        }
        int rest = dataFaker.getHowMuch() - instances.size();
        if (rest > 0) {
            int restDiv = rest / instances.size();
            int restMod = rest % instances.size();
            for (int i = 0; i < restDiv; i++) {
                instances.addAll(instances.stream().limit(instances.size()).collect(Collectors.toList()));

                instances.addAll(instances.stream().limit(restMod).collect(Collectors.toList()));
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

}
