/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.beans.Attribute;
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
}
