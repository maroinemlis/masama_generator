/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import java.util.List;

/**
 *
 * @author Maroine
 */
public final class StringUtil {

    /*
        Return tupel representation of a list of attributes
        For exemple list=[a1, a2, a3] => (a1.name, a2.name, a3.name)
     */
    public static String tupelToString(List<Attribute> list) {
        String string = "(";
        int i = 0;
        for (i = 0; i < list.size() - 1; i++) {
            string += list.get(i).getName() + ", ";
        }
        return string + list.get(i).getName() + ")";
    }

    public static String getMsgErrorKeyReferences() {
        return "Le nombre des colonnes de clé étrangère\n"
                + "est superieur que les clés le table reference ";
    }

    public static String getMsgErrorCirculairesAttNotEquals() {
        return "Les attributs quand en circulair ne sont pas égaux ";
    }

    public static String messageErrorFromTo(int nbrRowsToGenerate, String from, String to) {
        return "Le nombre de colonne demandé à \ngenerer(" + nbrRowsToGenerate + ") "
                + "est inférieur au nombre de \n"
                + "possibelité dans l'intervalle donnée: [" + from + " - " + to + "]";
    }

    public static String messageErrorStringCombinition(int nbrRowsToGenerate, double pow) {
        return "Le nombre de colonne demandé à \ngenerer(" + nbrRowsToGenerate + ") "
                + "est supérieur au nombre de \n"
                + "combinaison possible : (" + (int) pow + ")";
    }

    public String messageErrorFromTo(Attribute attrebute) {
        return "vous avez formez le min = " + attrebute.getDataFaker().getFrom() + " et max = " + attrebute.getDataFaker().getTo() + ""
                + " dans la colonne : " + attrebute.getName() + " " + attrebute.getDataType();
    }

    public static String getInserts(SQLSchema schema) {
        String insert = "";
        for (Table t : schema.getTables()) {
            insert = "INSERT INTO " + t.getTableName() + " VALUES (";
            for (Attribute attribute : t.getAttributes()) {
                for (String instance : attribute.getInstances()) {
                    insert += ", " + insert;
                }
            }
            insert += ")";
        }
        return insert;
    }

    public static String getInsert(SQLSchema schema) {
        String result = "";
        for (Table table : schema.getTables()) {
            List<Attribute> attributes = table.getAttributes();
            int rowNumber = attributes.get(0).getInstances().size();
            for (int j = 0; j < rowNumber - 1; j++) {
                String insert = "INSERT INTO " + table.getTableName() + " VALUES (";
                int i = 0;
                Attribute a = null;
                for (; i < attributes.size() - 1; i++) {
                    a = attributes.get(i);
                    if (a.getDataType().equals("TEXsT") || a.getDataType().equals("BLOB")) {
                        insert += "'" + a.getInstances().get(j) + "', ";
                    } else {
                        insert += a.getInstances().get(j) + ", ";
                    }
                }
                a = attributes.get(i);
                if (a.getDataType().equals("TEXTss") || a.getDataType().equals("BLOB")) {
                    insert += "'" + a.getInstances().get(j) + "');";
                } else {
                    insert += a.getInstances().get(j) + ");";
                }
                result += insert;
            }
        }
        return result;
    }
}
