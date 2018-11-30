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
import db.utils.StringUtil;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     * the checkSqlSchema check if we can generate or not data return final
     * value CHECKED_TRUE if it is possible to generate else return message
     * describe the reason way it is not possible to generate data
     *
     * @return String
     */
    public String checkSqlSchema() throws ParseException, SQLException {
        //check if circuler

        /*if (isCircular()) {
            return "Notre application ne génére pas les données pour les schémas circulaires ...";
        }*/
        //check from and to ::from<to
        //check ForingAndKPrimery todo:: rendre la method return true or false
        String result = checkForingAndKPrimery();
        if (!result.equals(CHECKED_TRUE)) {
            return result;
        }

        //check intervales
        boolean check;
        for (Table table : sqlSchema.getTables()) {
            int nbrRowsToGenerate = table.getHowMuch();
            for (Attribute attrebute : table.getAttributes()) {
                if (!checkFromTo(attrebute)) {
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

    public boolean isCircular() throws SQLException {
        boolean result = true;
        for (Table table : sqlSchema.getTables()) {
            boolean b = isCirculedInTable(table);
            if (b) {
            }
            return b;
        }
        return result;
    }

    List<String> listTable = new ArrayList();

    //todo :we need to get the name of table from reference
    private boolean isCirculedInTable(Table table) {
        boolean result = false;
        if (!listTable.contains(table.getTableName())) {
            listTable.add(table.getTableName());
            System.err.println(" ->" + listTable.toString());
            if (!table.getAttributes().get(0).getReference().equals(null)) {
                Attribute a = table.getAttributes().get(0);
                Attribute b = a.getReference();
                Attribute c = b.getReference();

                //result = isCirculedInTable(table.getAttributes(0).getReference().get(0));
            }
        } else {
            System.err.println(" ----------------END" + table.getTableName());
            result = true;
        }

        /*if (table.getForeignKeys().isEmpty()) {

        } else {

        }*/
        return result;
    }

    private String checkForingAndKPrimery() {
        String result = CHECKED_TRUE;

        for (Table table : sqlSchema.getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                try {
                    int nbrHowMushP = attribute.getDataFaker().getHowMuch();
                    int nbrHowMushF = attribute.getReference().getDataFaker().getHowMuch();
                    System.out.println(nbrHowMushF + ">" + nbrHowMushP);
                    if (nbrHowMushF > nbrHowMushP) {
                        return new StringUtil().getMsgErrorKeyReferences();
                    } else {
                        result = CHECKED_TRUE;
                    }
                } catch (NullPointerException e) {
                    //System.out.println("NullPointerException");
                }
                /*if (!attribute.getReference().equals(null)) {

                }*/
            }
            /*
            for (ForeignKey foreignKey : table.getForeignKeys()) {
                boolean isReferenceToPK = isReferenceToPK(foreignKey);
                if (isReferenceToPK
                        && (foreignKey.getReferences().getHowMuch() < table.getHowMuch())) {

                } else {
                    result = CHECKED_TRUE;
                }
            }
             */

        }

        return result;
    }

    /**
     * this class is for to check if it is possible to generate data or not
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
        //todo in the case thier are multi pk and fk
    }

    /**
     * TODO ): Bug : check this only if the son is unique or primary key
     *
     * @return boolean
     */
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

    /**
     * TODO ): Bug : it does not take into account the number of months
     *
     * @param attrebute
     * @return boolean
     */
    private boolean checkDate(Attribute attrebute, int nbrRowsToGenerate) throws ParseException {
        boolean result;
        String from = attrebute.getDataFaker().getFrom();
        String to = attrebute.getDataFaker().getTo();
        int numberOfDay = numberDaysBetween(from, to);
        //System.out.println(numberOfDay);
        if (numberOfDay >= nbrRowsToGenerate) {
            result = true;
        } else {
            msgError = new StringUtil().messageErrorFromTo(nbrRowsToGenerate, from, to);
            result = false;
        }
        return result;
    }

    /**
     * TODO ): Bug : it does not take into account the number of months todo ):
     * BUG: it shold work oly with unique
     *
     * @param attrebute
     * @return boolean
     */
    private int numberDaysBetween(String from, String to) throws ParseException {
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
     * todo :( BUG: it shold work oly with unique return True if it is possible
     * to generate the method check if the attrebute.getDataFaker().getTO() is
     * superior then attrebute.getDataFaker().getFrom() || example : 4
     * caractères || A à Z = 26 Lettres donc 26*26*26*26
     *
     * @param attrebute
     * @return boolean
     */
    private boolean checkString(Attribute attrebute, int nbrRowsToGenerate) {
        boolean result = true;
        if (attrebute.getDataType().equals("INT")
                || attrebute.getDataType().equals("TEXT")) {

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
        } else if (attrebute.getDataType().equals("DATE")) {

            result = true;
        }
        return result;
    }

    /**
     * return True if the MIN > MAX else false
     *
     * @param attrebute
     * @return boolean
     */
    private boolean checkFromTo(Attribute attrebute) {
        boolean result = true;
        String from = attrebute.getDataFaker().getFrom();
        String to = attrebute.getDataFaker().getTo();
        if (attrebute.getDataType().equals("INT")
                || attrebute.getDataType().equals("TEXT")) {
            int fromInt = Integer.valueOf(from);
            int toInt = Integer.valueOf(to);
            result = fromInt <= toInt;
            msgError = ((result) ? "" : new StringUtil().messageErrorFromTo(attrebute));
        } else if (attrebute.getDataType().equals("DATE")) {
            try {
                result = new DateUtil().CompareDate(from, to);
                msgError = ((result) ? "" : new StringUtil().messageErrorFromTo(attrebute));
            } catch (ParseException ex) {
                System.out.println("db.validation.PreCondetion.checkFromTo()");
                Logger.getLogger(PreCondetion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;

    }

}
