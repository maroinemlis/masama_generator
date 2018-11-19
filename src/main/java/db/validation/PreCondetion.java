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

        //check
        String result = checkForingAndKPrimery();
        if (!result.equals(CHECKED_TRUE)) {
            return result;
        }

        //check intervales
        boolean check;
        for (Table table : sqlSchema.getTables()) {
            int nbrRowsToGenerate = table.getHowMuch();
            for (Attribute attrebute : table.getAttributes()) {
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
                    default:
                        break;
                }
            }
        }
        return CHECKED_TRUE;
    }

    private String checkForingAndKPrimery() {

        String result = "No Foging key ";
        for (Table table : sqlSchema.getTables()) {
            List<ForeignKey> foreignKeys = table.getForeignKeys();
            int nbrRowsThisTable = table.getHowMuch();
            for (ForeignKey foreignKey : table.getForeignKeys()) {
                int nbrRowsTableReference = foreignKey.getReferences().getHowMuch();

                boolean b = foreignKey.getReferences().getAttribute("mID").isPrimary()
                        || foreignKey.getReferences().getAttribute("mID").isUnique();

                if (b && (nbrRowsTableReference >= nbrRowsThisTable)) {
                    result = CHECKED_TRUE;
                } else {
                    result = new StringUtil().getMsgErrorKeyReferences();
                }
            }
        }

        return result;
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
        // String from= attrebute.getDataFaker().getFrom();
        //String to = attrebute.getDataFaker().getTo();
        int numberOfDay = numberDayBetween(from, to);

        if (numberOfDay >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = new StringUtil().messageErrorFromTo(nbrRowsToGenerate, from, to);
            result = false;
        }
        return result;
    }

    private int numberDayBetween(String from, String to) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");

        Date toDate = dateFormat.parse(to);
        Date fromDate = dateFormat.parse(from);

        long diffInMillies = Math.abs(toDate.getTime() - fromDate.getTime());
        int result = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
        //System.out.println("------>"+ result);
        return result;

    }

}
