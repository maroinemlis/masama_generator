package pre_condition;

import beans.Attribute;
import beans.SQLSchema;
import beans.Table;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * this class is for to check if it is possible to generate data or not
 *
 * @author amirouche
 */
public class PreCondition extends Exception {

    private String errorMessage;

    private static PreCondition singlotonPreCondition = null;

    public static PreCondition getInstance() {
        if (singlotonPreCondition == null) {
            singlotonPreCondition = new PreCondition();
        }
        return singlotonPreCondition;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean checkSQLSchema() throws ParseException, SQLException {
        return isAttrCirculairesEquals() && isForingAndKPrimeryChecked() && isIntervalesChecked();
    }

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

    private boolean isForingAndKPrimeryChecked() {
        for (Table table : SQLSchema.getInstance().getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                for (Attribute reference : attribute.getReferences()) {
                    if ((attribute.isPrimary() || attribute.isUnique())
                            && attribute.getTable().getHowMuch() < reference.getTable().getHowMuch()) {
                        errorMessage = "Le nombre de génération de " + reference + " ne peut pas étre plus que "
                                + attribute + " car il s'agit d'un atrribut unique ou primary";

                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isIntervalesChecked() throws ParseException {
        for (Table table : SQLSchema.getInstance().getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                if (attribute.isUnique() || attribute.isPrimary()) {
                    boolean checked = true;
                    switch (attribute.getDataType()) {
                        case "INT":
                        case "INTEGER":
                        case "NUMBER":
                            if (!checkInt(attribute)) {
                                checked = false;
                            }
                            break;
                        case "DATE":
                            if (!checkDate(attribute)) {
                                checked = false;
                            }
                            break;
                        case "TEXT":
                            if (!checkString(attribute)) {
                                checked = false;
                            }
                            break;
                    }
                    if (checked) {
                        errorMessage = "L'interval des valeurs de " + attribute
                                + " est insuffusant pour générer " + attribute.getTable().getHowMuch();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * return true if it is possible to generate unique values TODO ): Bug :
     * check this only if the son is unique or primary key
     *
     * @param attribute
     * @return boolean
     */
    private boolean checkInt(Attribute attribute) {
        double from = Integer.valueOf(attribute.getDataFaker().getFrom());
        double to = Integer.valueOf(attribute.getDataFaker().getTo());
        if (attribute.getTable().getHowMuch() <= (to - from)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param attribute, nbrRowsToGenerate
     * @return boolean
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
     * @param from ,to
     * @return Integer
     */
    private long numberDaysBetween(String from, String to) throws ParseException {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);
        return DAYS.between(fromDate, toDate);
    }

    /**
     * to generate the method check if the attribute.getDataFaker().getTO() is
     * superior then attribute.getDataFaker().getFrom() || example : 4
     * caractères || A à Z = 26 Lettres donc 26*26*26*26
     *
     * @param attribute ,nbrRowsToGenerate
     * @return boolean
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
