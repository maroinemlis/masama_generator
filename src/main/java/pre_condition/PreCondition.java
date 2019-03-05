package pre_condition;

import beans.Attribute;
import beans.SQLSchema;
import beans.Table;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * this class is for to check if it is possible to generate data or not
 *
 * @author amirouche
 */
public class PreCondition extends Exception {

    private String errorMessage;

    private static PreCondition singlotonPreCondition = null;

    /**
     * Return instance of PreCondition
     *
     * @return
     */
    public static PreCondition getInstance() {
        if (singlotonPreCondition == null) {
            singlotonPreCondition = new PreCondition();
        }
        return singlotonPreCondition;
    }

    /**
     * Return the message error
     *
     * @return message error
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Return True if it is possible to generate values with this setting else
     * return false.
     *
     * @return True if it is possible to generate values.
     * @throws ParseException
     * @throws SQLException
     */
    public boolean checkSQLSchema() throws ParseException, SQLException {
        return isAttrCirculairesEquals() && isForingAndKPrimeryChecked() && isIntervalesChecked();
    }

    /**
     * Check if the all number of value to generate for attributes in the same
     * circular shema are equivalent.
     *
     * @return
     */
    private boolean isAttrCirculairesEquals() {
        List<Attribute> circularAttributs = new ArrayList<>();
        for (Table table : SQLSchema.getInstance().getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                if (attribute.isCircular()) {
                    circularAttributs.add(attribute);
                }
            }
        }
        for (Attribute attribute : circularAttributs) {
            if (!isHowMuchChecked(attribute, attribute)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if howMuch value is equivalent in circular schema
     *
     * @param attribute
     * @param _this
     * @return
     */
    private boolean isHowMuchChecked(Attribute attribute, Attribute _this) {
        boolean result = true;
        for (Attribute reference : attribute.getReferences()) {
            if (_this == reference) {
                break;
            }
            if (attribute.getTable().getHowMuch() != reference.getTable().getHowMuch()) {
                errorMessage = "Les attributs circulaires " + attribute + " et " + reference + " ont pas le même nombre de génération";
                return false;
            }
            result = result && isHowMuchChecked(reference, _this);
        }
        return result;
    }

    /**
     * Check if the unique foreign key attributes have less value then the
     * referenced attribute.
     *
     * @return
     */
    private boolean isForingAndKPrimeryChecked() {
        for (Table table : SQLSchema.getInstance().getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                for (Attribute reference : attribute.getReferences()) {
                    if ((attribute.isPrimary() || attribute.isUnique())
                            && attribute.getTable().getHowMuch() > reference.getTable().getHowMuch()) {
                        errorMessage = "Le nombre de génération de " + reference + " ne peut pas étre plus que "
                                + attribute + " car il s'agit d'un atrribut unique ou primary";
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check if it is possible to generate enough value in the given interval.
     *
     * @return true if it is possible to generate enough value
     * @throws ParseException
     */
    private boolean isIntervalesChecked() throws ParseException {
        Attribute a = null;
        for (Table table : SQLSchema.getInstance().getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                a = attribute;
                if (attribute.isUnique() || attribute.isPrimary()) {
                    switch (attribute.getDataType()) {
                        case "INT":
                        case "INTEGER":
                        case "NUMBER":
                            if (!checkInt(attribute)) {
                                errorMessage = "L'interval des valeurs de " + a
                                        + " est insuffusant pour générer " + a.getTable().getHowMuch();
                                return false;
                            }
                            break;
                        case "DATE":
                            if (!checkDate(attribute)) {
                                errorMessage = "L'interval des valeurs de " + a
                                        + " est insuffusant pour générer " + a.getTable().getHowMuch();
                                return false;

                            }
                            break;
                        case "TEXT":
                            if (!checkString(attribute)) {
                                errorMessage = "L'interval des valeurs de " + a
                                        + " est insuffusant pour générer " + a.getTable().getHowMuch();
                                return false;
                            }
                    }
                }
            }
        }
        return true;

    }

    /**
     * Check if it is possible to generate enough Integer value in the given
     * interval.
     *
     * @param attribute the attribute to check
     * @return true if it is possible to generate enough Integer value
     */
    private boolean checkInt(Attribute attribute) {
        int from = Integer.parseInt(attribute.getDataFaker().getFrom());
        int to = Integer.parseInt(attribute.getDataFaker().getTo());
        if (attribute.getTable().getHowMuch() <= (to - from)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if it is possible to generate enough Date value in the given
     * interval.
     *
     * @param attribute
     * @return true if it is possible to generate enough Date value
     */
    private boolean checkDate(Attribute attribute) throws ParseException {
        String from = attribute.getDataFaker().getFrom();
        String to = attribute.getDataFaker().getTo();
        int numberOfDay = (int) numberDaysBetween(from, to);
        if (numberOfDay >= attribute.getTable().getHowMuch()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return number of day between to dates
     *
     * @param from the first date
     * @param to second date
     * @return number of day between to dates
     * @throws DateTimeParseException if the text cannot be parsed.
     */
    private long numberDaysBetween(String from, String to) throws DateTimeParseException {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return DAYS.between(fromDate, toDate);
    }

    /**
     * Check if it is possible to generate enough value in the given interval.
     *
     * @param attribute
     * @return true if it is possible to generate enough value in the given
     * interval.
     */
    private boolean checkString(Attribute attribute) {
        boolean result = true;
        int from = Integer.valueOf(attribute.getDataFaker().getFrom());
        int to = Integer.valueOf(attribute.getDataFaker().getTo());
        double nbrCombinision = 0;
        for (int i = from; i <= to; i++) {
            nbrCombinision += Math.pow(26, i);
        }
        if (nbrCombinision >= attribute.getTable().getHowMuch()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
