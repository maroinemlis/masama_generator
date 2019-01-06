/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.validation;

import db.bean.Attribute;
import db.bean.ForeignKey;
import db.bean.SQLSchema;
import db.bean.Table;
import db.utils.DateUtil;
import db.utils.ShemaUtil;
import db.utils.StringUtil;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * this class is for to check if it is possible to generate data or not
 *
 * @author amirouche
 */
public class PreCondetion {

    SQLSchema sqlSchema;
    public static final String CHECKED_TRUE = "correct_generation";
    private String msgError;

    //todo : if thier  circuler the howMuch must equel
    /**
     * Constructor for class PreCondetion
     *
     * @param sqlSchema
     */
    public PreCondetion(SQLSchema sqlSchema) {
        this.sqlSchema = sqlSchema;
    }

    public String getMsgError() {
        return msgError;
    }

    /**
     * return final variable CHECKED_TRUE if there no error in the schema else
     * return the message error
     *
     * @throws ParseException, SQLException
     * @return String
     */
    /*
    public String checkSqlSchema() throws ParseException, SQLException {

        //check ForingAndKPrimery todo:: rendre la method return true or false
        String result = checkForingAndKPrimery();
        if (!result.equals(CHECKED_TRUE)) {
            return result;
        }
        //check intervales
        boolean check;

        for (Table table : sqlSchema.getTables()) {
            int nbrRowsToGenerate = table.getHowMuch();
            for (Attribute attribute : table.getAttributes()) {

                if (!checkFromTo(attribute)) {
                    return msgError;
                }
                if (attribute.getReferences().isEmpty()) {
                    String type = attribute.getDataType();
                    switch (type) {
                        case "INT":
                        case "INTEGER":
                            check = checkInt(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return msgError;
                            }
                            break;
                        case "DATE":
                            check = checkDate(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return msgError;
                            }
                            break;
                        case "TEXT":
                            check = checkString(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return msgError;
                            }
                            break;
                    }
                }

            }
        }
        return CHECKED_TRUE;
    }
     */
    public boolean checkSqlSchema() throws ParseException, SQLException {
        if (!ShemaUtil.isAttrCirculairesEquals(sqlSchema)) {
            msgError = StringUtil.getMsgErrorCirculairesAttNotEquals();
            return false;
        }
        //check ForingAndKPrimery todo:: rendre la method return true or false
        String result = checkForingAndKPrimery();
        if (!result.equals(CHECKED_TRUE)) {
            return false;
        }
        //check intervales
        boolean check;

        for (Table table : sqlSchema.getTables()) {
            int nbrRowsToGenerate = table.getHowMuch();
            for (Attribute attribute : table.getAttributes()) {

                if (!checkFromTo(attribute)) {
                    return false;
                }
                if (attribute.getReferences().isEmpty()) {
                    String type = attribute.getDataType();
                    switch (type) {
                        case "INT":
                        case "INTEGER":
                            check = checkInt(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return false;
                            }
                            break;
                        case "DATE":
                            check = checkDate(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return false;
                            }
                            break;
                        case "TEXT":
                            check = checkString(attribute, nbrRowsToGenerate);
                            if (!check) {
                                return false;
                            }
                            break;
                    }
                }

            }
        }
        return true;
    }

    /**
     * return true if a schema is circular else false
     *
     * @throws SQLException
     * @return boolean
     */
    public boolean isCircular() throws SQLException {
        boolean result = false;
        for (Table table : sqlSchema.getTables()) {
            result = isCirculedInTable(table);
        }
        return result;
    }

    List<String> listTable = new ArrayList();

    /**
     * todo :return true if a schema is circular else false used from
     * isCircular()
     *
     * @param table
     * @return boolean
     */
    private boolean isCirculedInTable(Table table) {
        boolean result = false;
        /*if (!listTable.contains(table.getTableName())) {
            listTable.add(table.getTableName());
            System.err.println(" ->" + listTable.toString());
            if (!table.getAttributes().get(0).getReference().equals(null)) {
                Attribute a = table.getAttributes().get(0);
                Attribute b = a.getReference();
                Attribute c = b.getReference();
            }
        } else {
            System.err.println(" ----------------END" + table.getTableName());
            result = true;
        }*/
        return result;
    }

    /**
     * return the String message error if the table T2 in A is primary key or
     * unique is reference to table T1 in B and T1 have less row then T1 else
     * return the final variable CHECKED_TRUE
     *
     * @return String
     */
    private String checkForingAndKPrimery() {
        String result = CHECKED_TRUE;

        for (Table table : sqlSchema.getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                try {
                    int nbrHowMushP = attribute.getDataFaker().getHowMuch();
                    int nbrHowMushF = attribute.getReferences()
                            .stream().map(a -> a.getDataFaker().getHowMuch()).min(Integer::compare).get();
                    //System.out.println(nbrHowMushF + ">" + nbrHowMushP);
                    if (nbrHowMushF > nbrHowMushP) {
                        return StringUtil.getMsgErrorKeyReferences();
                    } else {
                        result = CHECKED_TRUE;
                    }
                } catch (Exception e) {
                    //System.out.println("NullPointerException");
                }
            }

        }

        return result;
    }

    /**
     * return true if attribute is unique or primary key and reference to
     * another table
     *
     * @param foreignKey
     * @return boolean
     */
    private boolean isReferenceToPK(ForeignKey foreignKey) {
        boolean result = false;
        for (Attribute attribute : foreignKey.getPkTuple()) {
            if (attribute.isPrimary() || attribute.isUnique()) {
                return true;
            }
        }
        return result;
    }

    /**
     * return true if it is possible to generate unique values TODO ): Bug :
     * check this only if the son is unique or primary key
     *
     * @param attribute
     * @param nbrRowsToGenerate
     * @return boolean
     */
    private boolean checkInt(Attribute attribute, int nbrRowsToGenerate) {
        boolean result;
        double from = Integer.valueOf(attribute.getDataFaker().getFrom());
        double to = Integer.valueOf(attribute.getDataFaker().getTo());
        if (nbrRowsToGenerate <= (to - from)) {
            result = true;
        } else {
            msgError = StringUtil.messageErrorFromTo(nbrRowsToGenerate, String.valueOf(from), String.valueOf(to));
            result = false;
        }
        return result;
    }

    /**
     *
     *
     * @param attribute, nbrRowsToGenerate
     * @return boolean
     */
    private boolean checkDate(Attribute attribute, int nbrRowsToGenerate) throws ParseException {
        //TODO ): Bug : it does not take into account the number of months
        boolean result;
        String from = attribute.getDataFaker().getFrom();
        String to = attribute.getDataFaker().getTo();
        int numberOfDay = numberDaysBetween(from, to);
        //System.out.println(numberOfDay);
        if (numberOfDay >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = StringUtil.messageErrorFromTo(nbrRowsToGenerate, from, to);
            result = false;
        }
        return result;
    }

    /**
     *
     *
     * @param from ,to
     * @return int
     */
    private int numberDaysBetween(String from, String to) throws ParseException {
        // * TODO ): Bug : it does not take into account the number of months todo ):
        // BUG: it shold work oly with unique
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date toDate = dateFormat.parse(to);
        Date fromDate = dateFormat.parse(from);
        /*
        int diffInDays = (int) ((toDate.getTime() - fromDate.getTime())
                / (1000 * 60 * 60 * 24)) + 1;
        System.err.println("D " + diffInDays);*/
        long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
        int result = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
        return result;

    }

    /**
     * to generate the method check if the attribute.getDataFaker().getTO() is
     * superior then attribute.getDataFaker().getFrom() || example : 4
     * caractères || A à Z = 26 Lettres donc 26*26*26*26
     *
     * @param attribute ,nbrRowsToGenerate
     * @return boolean
     */
    private boolean checkString(Attribute attribute, int nbrRowsToGenerate) {
        // todo :( BUG: it shold work oly with unique return True if it is possible
        boolean result = true;
        int from = Integer.valueOf(attribute.getDataFaker().getFrom());
        int to = Integer.valueOf(attribute.getDataFaker().getTo());
        double nbrCombinision = 0;
        for (int i = from; i <= to; i++) {
            nbrCombinision += Math.pow(26, i);
        }
        if (nbrCombinision >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = StringUtil.messageErrorStringCombinition(nbrRowsToGenerate, nbrCombinision);
            result = false;
        }
        return result;
    }

    /**
     * return True if the MIN > MAX else false
     *
     * @param attribute
     * @return boolean
     */
    private boolean checkFromTo(Attribute attribute) throws ParseException {
        boolean result = true;
        if (attribute.getReferences().isEmpty()) {
            return true;
        }
        String from = attribute.getDataFaker().getFrom();
        String to = attribute.getDataFaker().getTo();
        switch (attribute.getDataType()) {
            case "TEXT":
            case "INT":
                result = Integer.parseInt(from) <= Integer.parseInt(to);
                msgError = result ? "" : new StringUtil().messageErrorFromTo(attribute);
                break;
            case "DATE":
                result = new DateUtil().CompareDate(from, to);
                msgError = result ? "" : new StringUtil().messageErrorFromTo(attribute);
                break;
        }
        return result;
    }

}
