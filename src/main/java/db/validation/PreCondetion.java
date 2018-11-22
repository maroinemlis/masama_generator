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
import db.utils.StringUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public PreCondetion(SQLSchema sqlSchema) {
        this.sqlSchema = sqlSchema;
    }

    /**
     * the checkSqlScema check if we can generate or not data return final value
     * CHECKED_TRUE if it is possible to generate else return message describe
     * the reason way it is not possible to generate data
     *
     * @return
     */
    public String checkSqlScema() throws ParseException {
        //check from and to ::from<to

        //check ForingAndKPrimery
        String result = checkForingAndKPrimery();
        if (!result.equals(CHECKED_TRUE)) {
            return result;
        }

        //check intervales
        boolean check;
        for (Table table : sqlSchema.getTables()) {
            int nbrRowsToGenerate = table.getHowMuch();
            for (Attribute attrebute : table.getAttributes()) {
                if (checkFromTo(attrebute)) {
                    return msgError;
                }
                String type = attrebute.getDataType();
                switch (type) {
                    case "INT":
                    case "INTEGER":
                    case "DOUBLE":
                    case "FLOAT":
                        check = checkInt(attrebute, nbrRowsToGenerate);
                        if (!check) {
                            return msgError;
                        }
                        break;
                    case "DATE":
                        check = checkDate(attrebute, nbrRowsToGenerate);
                        if (!check) {
                            return msgError;
                        }
                    case "TEXT":
                        check = checkString(attrebute, nbrRowsToGenerate);
                        if (!check) {
                            return msgError;
                        }
                    default:
                        break;
                }
            }
        }
        return CHECKED_TRUE;
    }

    private String checkForingAndKPrimery() {

        String result = CHECKED_TRUE;

        for (Table table : sqlSchema.getTables()) {
            for (ForeignKey foreignKey : table.getForeignKeys()) {

                boolean isReferenceToPK = isReferenceToPK(foreignKey);
                if (isReferenceToPK
                        && (foreignKey.getReferences().getHowMuch() < table.getHowMuch())) {
                    result = new StringUtil().getMsgErrorKeyReferences();
                } else {
                    result = CHECKED_TRUE;
                }
            }
        }

        return result;
    }

    private boolean isReferenceToPK(ForeignKey foreignKey) {
        boolean result = false;
        for (Attribute attribute : foreignKey.getPkTuple()) {
            if (attribute.isPrimary() || attribute.isUnique()) {
                return true;
            }

        }
        return result;
        //todo in the case thier are multi pk and fk
    }

    private boolean checkInt(Attribute attrebute, int nbrRowsToGenerate) {
        boolean result;
        double from = Integer.valueOf(attrebute.getDataFaker().getFrom());
        double to = Integer.valueOf(attrebute.getDataFaker().getTo());
        if (nbrRowsToGenerate <= (to - from)) {
            result = true;
        } else {
            msgError = new StringUtil().messageErrorFromTo(nbrRowsToGenerate, String.valueOf(from), String.valueOf(to));
            result = false;
        }
        return result;
    }

    private boolean checkDate(Attribute attrebute, int nbrRowsToGenerate) throws ParseException {
        boolean result;
        String from = attrebute.getDataFaker().getFrom();
        String to = attrebute.getDataFaker().getTo();
        int numberOfDay = numberDaysBetween(from, to);

        if (numberOfDay >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = new StringUtil().messageErrorFromTo(nbrRowsToGenerate, from, to);
            result = false;
        }
        return result;
    }

    private int numberDaysBetween(String from, String to) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        Date toDate = dateFormat.parse(to);
        Date fromDate = dateFormat.parse(from);

        long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
        int result = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
        //System.out.println("------>"+ result);
        return result;

    }

    private boolean checkString(Attribute attrebute, int nbrRowsToGenerate) {
        //4 caractères
        //A à Z = 26 Lettres
        //donc 26*26*26*26
        boolean result;
        int from = Integer.valueOf(attrebute.getDataFaker().getFrom());
        int to = Integer.valueOf(attrebute.getDataFaker().getTo());
        double nbrCombinision = 0;
        for (int i = from; i <= to; i++) {
            nbrCombinision += Math.pow(26, i);
        }
        if (nbrCombinision >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = new StringUtil().messageErrorStringCombinition(nbrRowsToGenerate, nbrCombinision);
            result = false;
        }
        return result;
    }

    /**
     * the method check if the attrebute.getDataFaker().getTO() is superior then
     * attrebute.getDataFaker().getFrom() || example : 4 caractères || A à Z =
     * 26 Lettres donc 26*26*26*26
     *
     * @param attrebute
     * @return boolean
     */
    private boolean checkFromTo(Attribute attrebute) {
        int from = Integer.valueOf(attrebute.getDataFaker().getFrom());
        int to = Integer.valueOf(attrebute.getDataFaker().getTo());
        boolean result = from > to;
        msgError = ((result) ? new StringUtil().messageErrorFromTo(attrebute) : "");
        return result;
    }

}
