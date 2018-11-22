/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import java.util.List;

/**
 *
 * @author Maroine
 */
public class StringUtil {

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

    public String getMsgErrorKeyReferences() {
        return "Le nombre des colonnes de clé étrangère\n"
                + "est superieur que les clés le table reference ";
    }

    public String messageErrorFromTo(int nbrRowsToGenerate, String from, String to) {
        return "Le nombre de colonne demander à \ngeneré(" + nbrRowsToGenerate + ") "
                + "est inférieur au nombre de \n"
                + "possibelité dans l'intervalle donnée: [" + from + " - " + to + "]";
    }

    public String messageErrorStringCombinition(int nbrRowsToGenerate, double pow) {
        return "Le nombre de colonne demander à \ngeneré(" + nbrRowsToGenerate + ") "
                + "est supérieur au nombre de \n"
                + "combinision possible : (" + (int) pow + ")";
    }

    public String messageErrorFromTo(Attribute attrebute) {
        return "vous avez formez le min =" + attrebute.getDataFaker().getFrom() + " et max =" + attrebute.getDataFaker().getTo() + ""
                + "dans la colonne : " + attrebute.getName() + " " + attrebute.getDataType();
    }

}
